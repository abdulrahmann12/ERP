package com.learn.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.AdminCreateUserRequestDTO;
import com.learn.erp.dto.AuthResponse;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.EmailRequestDTO;
import com.learn.erp.dto.LoginRequestDTO;
import com.learn.erp.dto.ResetPasswordRequestDTO;
import com.learn.erp.dto.UserChangePasswordRequestDTO;
import com.learn.erp.model.User;
import com.learn.erp.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth Controller", description = "API for user authentication and authorization (login, resst password, etc).")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	
	@Operation(
			summary = "User login",
		    description = "Authenticate user using username/email and password and return access and refresh tokens."
	)
	@PostMapping("/login")
	public ResponseEntity<BasicResponse> login(@Valid @RequestBody LoginRequestDTO loginRequest){
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		AuthResponse authResponse = authService.login(loginRequest);
		return ResponseEntity.ok(new BasicResponse(Messages.LOGIN_SUCCESS,authResponse));
	}
	
	@Operation(
		    summary = "Refresh access token",
		    description = "Generate a new access token using a valid refresh token from the request."
	)
	@PostMapping("/refresh-token")
	public ResponseEntity<BasicResponse> refreshToken(HttpServletRequest request) {
	    AuthResponse response = authService.refreshToken(request);
	    return ResponseEntity.ok(new BasicResponse(Messages.NEW_TOKEN_GENERATED, response));
	}
	
	@Operation(
		    summary = "Logout user",
		    description = "Invalidate the current refresh token to log the user out."
	)
	@PostMapping("/logout")
	public ResponseEntity<BasicResponse> logout(HttpServletRequest request) {
	    authService.logoutByRequest(request);
	    return ResponseEntity.ok(new BasicResponse(Messages.LOGOUT_SUCCESS));
	}
	
	@Operation(
		    summary = "Create new user",
		    description = "Admin creates a new user account with a specific role."
	)
	@PostMapping("/create-user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> register(@RequestBody AdminCreateUserRequestDTO user){
		return ResponseEntity.ok(new BasicResponse(Messages.CREATE_NEW_USER,authService.craateUser(user)));
	}
	
	@Operation(
		    summary = "Change user password",
		    description = "Authenticated user changes their current password."
	)
	@PostMapping("/change-password")
	public ResponseEntity<BasicResponse> changePassword(@AuthenticationPrincipal User user,
	                                                    @Valid @RequestBody UserChangePasswordRequestDTO request){
	    authService.changePassword(user.getEmail(), request);
	    return ResponseEntity.ok(new BasicResponse(Messages.CHANGE_PASSWORD));
	}
	
	@Operation(
		    summary = "Regenerate verification code",
		    description = "Send a new verification code to the user’s email."
	)
	@PostMapping("/regenerate-code")
	public ResponseEntity<BasicResponse> regenerateCode(@AuthenticationPrincipal User user) {

	    authService.reGenerateCode(user.getEmail());
	    return ResponseEntity.ok(new BasicResponse(Messages.CODE_SENT));
	}

	@Operation(
		    summary = "Forget password",
		    description = "Send a reset code to the user's registered email address."
	)
	@PostMapping("/forget-password")
	public ResponseEntity<BasicResponse> forgetPassword(@Valid @RequestBody EmailRequestDTO email){
			authService.forgotPassword(email.getEmail());
			return ResponseEntity.ok(new BasicResponse(Messages.CODE_SENT));
	}
	
	@Operation(
		    summary = "Reset password",
		    description = "Reset the password using the verification code sent via email."
	)
	@PostMapping("/reset-password")
	public ResponseEntity<BasicResponse> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswodDTO){
			authService.resetPassword(resetPasswodDTO);
			return ResponseEntity.ok(new BasicResponse(Messages.CHANGE_PASSWORD));
	}
}