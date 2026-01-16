package com.siklus.application.controller;

import com.siklus.application.LoginRequest;
import com.siklus.application.model.User;
import com.siklus.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        User loggedIn = userService.login(request);
        return ResponseEntity.ok(loggedIn);
    }
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return userService.register(user);
    }
}
