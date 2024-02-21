package com.example.shortlinkapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("")
public class UserController {
    @GetMapping("/user/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public Principal user(@PathVariable Integer userID, Principal user) {
        return user;
    }

    @GetMapping("/home/{userID}")
    public String home(@PathVariable Integer userID) {
        return "Log in success!";
    }

}
