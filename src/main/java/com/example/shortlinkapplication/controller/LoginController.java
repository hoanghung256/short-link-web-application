package com.example.shortlinkapplication.controller;

import com.example.shortlinkapplication.dto.*;
import com.example.shortlinkapplication.entity.AuthProvider;
import com.example.shortlinkapplication.entity.ConfirmationToken;
import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.repository.UserRepository;
import com.example.shortlinkapplication.security.TokenProvider;
import com.example.shortlinkapplication.service.EmailAuthenticationToken;
import com.example.shortlinkapplication.service.LoginService;
import com.example.shortlinkapplication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/loginWithToken")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.signin(loginRequest));
    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) {
        boolean isConfirmed = loginService.confirmToken(token, request, response);
        System.out.println(isConfirmed);
        if (isConfirmed) {
            try {
                System.out.println();
                response.sendRedirect("/user/me");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Token is not confirmed");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignUpRequest signUpRequest) throws BadRequestException {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
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
