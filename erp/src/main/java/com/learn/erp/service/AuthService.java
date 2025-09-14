package com.learn.erp.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.AdminCreateUserRequestDTO;
import com.learn.erp.dto.AdminViewUserResponseDTO;
import com.learn.erp.dto.AuthResponse;
import com.learn.erp.dto.LoginRequestDTO;
import com.learn.erp.dto.ResetPasswordRequestDTO;
import com.learn.erp.dto.UserChangePasswordRequestDTO;
import com.learn.erp.events.PasswordCodeRegeneratedEvent;
import com.learn.erp.events.PasswordResetRequestedEvent;
import com.learn.erp.events.UserRegisteredEvent;
import com.learn.erp.exception.DepartmentNotFoundException;
import com.learn.erp.exception.EmailAlreadyExistsException;
import com.learn.erp.exception.InvalidCurrentPasswordException;
import com.learn.erp.exception.InvalidResetCodeException;
import com.learn.erp.exception.InvalidTokenException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.exception.UsernameAlreadyExistsException;
import com.learn.erp.mapper.UserMapper;
import com.learn.erp.model.Department;
import com.learn.erp.model.User;
import com.learn.erp.model.User.Role;
import com.learn.erp.repository.DepartmentRepository;
import com.learn.erp.repository.TokenRepository;
import com.learn.erp.repository.UserRepository;
import static com.learn.erp.rabbitconfig.RabbitConstants.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;
	private final DepartmentRepository departmentRepository;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	public AuthResponse login(LoginRequestDTO loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
				.or(() -> userRepository.findByEmail(loginRequest.getUsernameOrEmail()))
				.orElseThrow(UserNotFoundException::new);
		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		jwtService.revokeAllUserTokens(user);
		jwtService.saveUserToken(user, accessToken);
		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional
	public AdminViewUserResponseDTO craateUser(@Valid AdminCreateUserRequestDTO dto) {
		User user = userMapper.toEntity(dto);

		userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
			throw new EmailAlreadyExistsException();
		});

		userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
			throw new UsernameAlreadyExistsException();
		});

		Department department = departmentRepository.findById(dto.getDepartmentId())
				.orElseThrow(DepartmentNotFoundException::new);

		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setActive(true);
		user.setDepartment(department);
		User savedUser = userRepository.save(user);

		UserRegisteredEvent event = new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(),
				savedUser.getFullName(), LocalDateTime.now());
		rabbitTemplate.convertAndSend(AUTH_EXCHANGE, USER_REGISTERED_KEY, event);

		return userMapper.toAdminViewUserDTO(savedUser);
	}

	public void forgotPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		String resetCode = generateConfirmationCode();
		user.setRequestCode(resetCode);
		userRepository.save(user);

		PasswordResetRequestedEvent event = new PasswordResetRequestedEvent(user.getId(), user.getUsername(),
				user.getEmail(), resetCode, LocalDateTime.now());
		rabbitTemplate.convertAndSend(AUTH_EXCHANGE, PASSWORD_RESET_KEY, event);

	}

	public void resetPassword(ResetPasswordRequestDTO resetPasswodDTO) {
		User user = userRepository.findByEmail(resetPasswodDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException());
		if (!resetPasswodDTO.getCode().equals(user.getRequestCode())) {
			throw new InvalidResetCodeException();
		}
		user.setPassword(passwordEncoder.encode(resetPasswodDTO.getNewPassword()));
		user.setRequestCode(null);
		userRepository.save(user);

	}

	public void changePassword(String email, UserChangePasswordRequestDTO request) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new InvalidCurrentPasswordException();
		}
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
	}

	public void reGenerateCode(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		String resetCode = generateConfirmationCode();
		user.setRequestCode(resetCode);
		userRepository.save(user);

		rabbitTemplate.convertAndSend(AUTH_EXCHANGE, PASSWORD_CODE_REGENERATED_KEY,
				new PasswordCodeRegeneratedEvent(user.getEmail(), user.getUsernameField(), resetCode));

	}

	public User getUserByEmail(String username) {
		return userRepository.findByUsername(username).or(() -> userRepository.findByEmail(username))
				.orElseThrow(UserNotFoundException::new);
	}

	@Transactional
	public void logout(String token) {
		var storedToken = tokenRepository.findByToken(token).orElse(null);
		if (storedToken != null && !storedToken.isExpired()) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
		} else {
			throw new InvalidTokenException(Messages.ALREADY_LOGGED_OUT);
		}
	}

	@Transactional
	public AuthResponse refreshToken(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new InvalidTokenException(Messages.INVALID_REFRESH_TOKEN);
		}

		final String refreshToken = authHeader.substring(7);
		final String username = jwtService.extractUsername(refreshToken);

		if (username == null) {
			throw new InvalidTokenException(Messages.COULD_NOT_EXTRACT_USER);
		}

		User user = getUserByEmail(username);

		if (!jwtService.validateToken(refreshToken, user)) {
			throw new InvalidTokenException(Messages.INVALID_REFRESH_TOKEN);
		}

		String accessToken = jwtService.generateToken(user);
		jwtService.revokeAllUserTokens(user);
		jwtService.saveUserToken(user, accessToken);
		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional
	public void logoutByRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new InvalidTokenException(Messages.TOKEN_NOT_FOUND);
		}
		String token = authHeader.substring(7);
		logout(token);
	}

	private String generateConfirmationCode() {
		Random random = new Random();
		int code = 1000 + random.nextInt(90000);
		return String.valueOf(code);
	}

	@Transactional
	public AuthResponse loginOrRegisterOAuthUser(OAuth2User oAuth2User) {
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");

		User user = userRepository.findByEmail(email).orElse(null);

		Department department = departmentRepository.findById(2l).orElseThrow(DepartmentNotFoundException::new);

		if (user == null) {
			user = new User();
			user.setEmail(email);
			user.setFullName(name);
			user.setUsername(email.split("@")[0]);
			user.setActive(true);
			user.setDepartment(department);
			user.setRole(Role.EMPLOYEE);

			user.setPassword(passwordEncoder.encode(email));
			user = userRepository.save(user);
		}

		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		jwtService.revokeAllUserTokens(user);
		jwtService.saveUserToken(user, accessToken);

		return new AuthResponse(accessToken, refreshToken);
	}
}