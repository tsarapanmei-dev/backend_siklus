package com.siklus.application.service;

import com.siklus.application.LoginRequest;
import com.siklus.application.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User login(LoginRequest request);
    ResponseEntity<?> register(User user);
}
