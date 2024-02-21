package com.example.shortlinkapplication.config;

import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.repository.UserRepository;
import com.example.shortlinkapplication.service.CustomOAuth2UserService;
import com.example.shortlinkapplication.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private UserRepository userRepository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Start filer chain");
        http
                .csrf(c -> c.csrfTokenRepository((CookieCsrfTokenRepository.withHttpOnlyFalse())))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .successHandler(authenticationSuccessHandler())
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService)));
        System.out.println("Finish");
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // sendRedirect()

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String targetUrl = determineTargetUrl(authentication);
                redirectStrategy.sendRedirect(request, response, targetUrl);
            }

            private String determineTargetUrl(Authentication authentication) {
                DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

                System.out.println("loadUser working.....");
                String email = oidcUser.getAttribute("email");
                String firstName = oidcUser.getAttribute("given_name");
                String lastName = oidcUser.getAttribute("family_name");
                System.out.println(email);
                System.out.println(firstName);

                User user = userRepository.findByEmail(email);
                System.out.println(user);
                if (user == null) {
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    userRepository.save(user);
                    System.out.println("Saved");
                    return "/user/" + user.getUserID();
                } else {
                    return "/home/" + user.getUserID();
                }
            }
        };

    }
}
