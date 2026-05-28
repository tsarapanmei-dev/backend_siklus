package com.siklus.application.service;

import com.siklus.application.LoginRequest;
import com.siklus.application.dto.ForgotPasswordRequest;
import com.siklus.application.dto.RegisterRequest;
import com.siklus.application.model.User;
import org.springframework.http.ResponseEntity;
import com.siklus.application.dto.VerifyOtpRequest;

public interface UserService {
    User login(LoginRequest request);
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> forgotPassword(ForgotPasswordRequest request);

    ResponseEntity<?> sendRegisterOtp(String email);
    ResponseEntity<?> verifyRegisterOtp(VerifyOtpRequest request);
    ResponseEntity<?> sendForgotPasswordOtp(String email);
    ResponseEntity<?> verifyForgotPasswordOtp(VerifyOtpRequest request, String newPassword);
}
