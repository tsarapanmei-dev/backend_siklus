package com.siklus.application.service.impl;

import com.siklus.application.LoginRequest;
import com.siklus.application.exception.ResourceNotFoundException;
import com.siklus.application.model.User;
import com.siklus.application.repository.UserRepository;
import com.siklus.application.service.UserService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User login(LoginRequest request) {
        User user = repo.findByEmailUser(request.getEmail_user())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak Ditemukan"));

        if (!user.getPass().equals(request.getPass())) {
            throw new ResourceNotFoundException("Password Salah !");
        }
        return user;
    }

    @Override
    public ResponseEntity<?> register(User user) {
        // Cek apakah email sudah digunakan
        if (repo.findByEmailUser(user.getEmailUser()).isPresent()) {
            // balasan dengan status 400 (Bad Request)
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email sudah digunakan!"));
        }
        User savedUser = repo.save(user);
        return ResponseEntity
                .ok(Map.of(
                        "message", "Registrasi berhasil!",
                        "user", savedUser
                ));
    }
}

