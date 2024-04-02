package com.example.shortlinkapplication.controller;

import com.example.shortlinkapplication.dto.ApiResponse;
import com.example.shortlinkapplication.dto.LoginRequest;
import com.example.shortlinkapplication.dto.LoginResponse;
import com.example.shortlinkapplication.dto.SignUpRequest;
import com.example.shortlinkapplication.entity.AuthProvider;
import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.repository.UserRepository;
import com.example.shortlinkapplication.security.TokenProvider;
import com.example.shortlinkapplication.security.oauth.OAuth2AuthenticationFailureHandler;
import com.example.shortlinkapplication.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.example.shortlinkapplication.service.LoginService;
import com.example.shortlinkapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  private final LoginService loginService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final UserService userService;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(loginService.signin(loginRequest));
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok("Logout successful");
  }

  // if token is confirmed will create new jwt after
  @GetMapping("/confirm")
  public ResponseEntity<LoginResponse> confirm(@RequestParam("token") String token,
      HttpServletRequest request, HttpServletResponse response) {
    Integer userID = loginService.confirmToken(token, request, response);

    // create new user authenticated
    UserDetails userDetails = userService.loadUserById(userID);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    authentication.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.createToken(authentication);
    log.info("JWT: {}", jwt);

    LoginResponse loginResponse = new LoginResponse(jwt);

    // Add token to the Authorization header
    response.addHeader("Authorization", "Bearer " + jwt);

    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/error")
  public String handleError(@RequestParam("message") String message) {
    return "Error: " + message;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest)
      throws BadRequestException {
    if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
      throw new BadRequestException("Email address already in use.");
    }

    // Creating user's account
    User user = new User();
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setAuthProvider(AuthProvider.local);

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/user/me")
        .buildAndExpand(result.getUserID()).toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully@"));
  }
}
