package com.example.shortlinkapplication.security.oauth;

import com.example.shortlinkapplication.config.AppProperties;
import com.example.shortlinkapplication.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public class EmailVerifySuccessHandler extends OAuth2AuthenticationSuccessHandler{
    EmailVerifySuccessHandler(TokenProvider tokenProvider, AppProperties appProperties, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        super(tokenProvider, appProperties, httpCookieOAuth2AuthorizationRequestRepository);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
