package com.learn.erp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.erp.dto.AuthResponse;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        AuthResponse authResponse = authService.loginOrRegisterOAuthUser(oAuth2User);

        response.setContentType("application/json");
        response.getWriter().write(
            new ObjectMapper().writeValueAsString(
                new BasicResponse(Messages.GOOGLE_LOGIN_SUCCESS, authResponse)
            )
        );
    }
}