package com.example.shortlinkapplication.controller;

import com.example.shortlinkapplication.dto.LoginRequest;
import com.example.shortlinkapplication.dto.LoginResponse;
import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signin")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.signin(loginRequest));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return loginService.confirmToken(token);
    }
}
