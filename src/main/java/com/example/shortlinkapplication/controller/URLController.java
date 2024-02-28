package com.example.shortlinkapplication.controller;

import com.example.shortlinkapplication.dto.LoginRequest;
import com.example.shortlinkapplication.dto.LoginResponse;
import com.example.shortlinkapplication.dto.URLRequest;
import com.example.shortlinkapplication.dto.URLResponse;
import com.example.shortlinkapplication.exception.ExpiredKeyException;
import com.example.shortlinkapplication.exception.KeyNotFoundException;
import com.example.shortlinkapplication.service.URLService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shorten")
public class URLController {
    @Autowired
    private URLService urlService;

//    @PostMapping
//    public URLResponse generateUrl (@RequestBody URLRequest urlRequest, HttpServletRequest request) {
//        String longUrl = urlRequest.getOriginalURL();
//        System.out.println("get long url: " + longUrl);
//        //urlService.setServletRequest(request);
//        return urlService.callShortUrl(longUrl);
//    }

//    @CrossOrigin
//    @PostMapping
//    public ResponseEntity<URLResponse> generateUrl (@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(loginService.signin(loginRequest));
//    }
}
