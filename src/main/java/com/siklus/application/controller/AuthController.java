package com.siklus.application.controller;

import com.siklus.application.LoginRequest;
import com.siklus.application.dto.ForgotPasswordRequest;
import com.siklus.application.dto.RegisterRequest;
import com.siklus.application.dto.VerifyOtpRequest;
import com.siklus.application.model.User;
import com.siklus.application.service.UserService;
import com.siklus.application.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);
        LoginResponse response = new LoginResponse(
                user.getIdUser(),
                user.getEmailUser(),
                user.getRwUser()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest request) {return userService.register(request);}

    @PostMapping("/send-register-otp")
    public ResponseEntity<?> sendRegisterOtp(@RequestBody Map<String, String> body) {
        return userService.sendRegisterOtp(body.get("email_user"));
    }

    @PostMapping("/verify-register-otp")
    public ResponseEntity<?> verifyRegisterOtp(@RequestBody VerifyOtpRequest request) {
        return userService.verifyRegisterOtp(request);
    }

    @PostMapping("/send-forgot-otp")
    public ResponseEntity<?> sendForgotOtp(@RequestBody Map<String, String> body) {
        return userService.sendForgotPasswordOtp(body.get("email_user"));
    }

    @PostMapping("/verify-forgot-otp")
    public ResponseEntity<?> verifyForgotOtp(@RequestBody Map<String, String> body) {
        VerifyOtpRequest request = new VerifyOtpRequest();
        request.setEmailUser(body.get("email_user"));
        request.setOtpCode(body.get("otp_code"));
        String newPassword = body.get("new_password");
        return userService.verifyForgotPasswordOtp(request, newPassword);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return userService.forgotPassword(request);
    }
}