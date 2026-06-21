package com.siklus.application.service.impl;

import com.siklus.application.LoginRequest;
import com.siklus.application.dto.BaseResponse;
import com.siklus.application.dto.ForgotPasswordRequest;
import com.siklus.application.dto.RegisterRequest;
import com.siklus.application.dto.VerifyOtpRequest;
import com.siklus.application.exception.ResourceNotFoundException;
import com.siklus.application.model.User;
import com.siklus.application.repository.UserRepository;
import com.siklus.application.service.EmailService;
import com.siklus.application.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private static final long OTP_VALID_MINUTES = 10;

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(900000) + 100000);
    }

    @Override
    public User login(LoginRequest request) {
        User user = repo.findByEmailUser(request.getEmail_user())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        if (!user.isVerified()) {
            throw new ResourceNotFoundException("Email belum diverifikasi. Cek email kamu.");
        }

        if (!passwordEncoder.matches(request.getPass(), user.getPass())) {
            throw new ResourceNotFoundException("Password salah!");
        }
        return user;
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        Optional<User> existingUserOpt = repo.findByEmailUser(request.getEmailUser());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (existingUser.isVerified()) {
                return ResponseEntity.badRequest()
                        .body(new BaseResponse("Email sudah digunakan!"));
            }

            String otp = generateOtp();
            existingUser.setOtpCode(otp);
            existingUser.setOtpExpiry(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(OTP_VALID_MINUTES));
            existingUser.setRwUser(request.getRwUser());
            existingUser.setPass(passwordEncoder.encode(request.getPass()));

            try {
                emailService.sendOtp(existingUser.getEmailUser(), otp, "Verifikasi Akun");
                repo.save(existingUser);
                return ResponseEntity.ok(
                        new BaseResponse("Akun belum terverifikasi. OTP baru telah dikirim ke email kamu.")
                );
            } catch (Exception e) {
                return ResponseEntity.internalServerError()
                        .body(new BaseResponse("Gagal mengirim OTP. Silakan coba lagi."));
            }
        }

        User user = new User();
        user.setEmailUser(request.getEmailUser());
        user.setRwUser(request.getRwUser());
        user.setPass(passwordEncoder.encode(request.getPass()));
        user.setVerified(false);

        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(OTP_VALID_MINUTES));

        try {
            emailService.sendOtp(user.getEmailUser(), otp, "Verifikasi Akun");
            repo.save(user);
            return ResponseEntity.ok(
                    new BaseResponse("Registrasi berhasil! Cek email kamu untuk kode OTP.")
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new BaseResponse("Gagal mengirim OTP. Silakan coba lagi."));
        }
    }

    @Override
    public ResponseEntity<?> sendRegisterOtp(String email) {
        User user = repo.findByEmailUser(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email tidak ditemukan"));

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body(new BaseResponse("Akun sudah terverifikasi."));
        }

        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(OTP_VALID_MINUTES));
        repo.save(user);

        emailService.sendOtp(email, otp, "Verifikasi Akun");
        return ResponseEntity.ok(new BaseResponse("OTP telah dikirim ulang ke email kamu."));
    }

    @Override
    public ResponseEntity<?> verifyRegisterOtp(VerifyOtpRequest request) {
        User user = repo.findByEmailUser(request.getEmailUser())
                .orElseThrow(() -> new ResourceNotFoundException("Email tidak ditemukan"));

        if (user.getOtpCode() == null || !user.getOtpCode().equals(request.getOtpCode())) {
            return ResponseEntity.badRequest().body(new BaseResponse("Kode OTP salah!"));
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now(ZoneOffset.UTC).isAfter(user.getOtpExpiry())) {
            return ResponseEntity.badRequest().body(new BaseResponse("Kode OTP sudah kedaluwarsa!"));
        }

        user.setVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        repo.save(user);

        return ResponseEntity.ok(new BaseResponse("Email berhasil diverifikasi! Silakan masuk."));
    }

    @Override
    public ResponseEntity<?> sendForgotPasswordOtp(String email) {
        User user = repo.findByEmailUser(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email tidak ditemukan"));

        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(OTP_VALID_MINUTES));
        repo.save(user);

        emailService.sendOtp(email, otp, "Reset Password");
        return ResponseEntity.ok(new BaseResponse("OTP telah dikirim ke email kamu."));
    }

    @Override
    public ResponseEntity<?> verifyForgotPasswordOtp(VerifyOtpRequest request, String newPassword) {
        User user = repo.findByEmailUser(request.getEmailUser())
                .orElseThrow(() -> new ResourceNotFoundException("Email tidak ditemukan"));

        if (user.getOtpCode() == null || !user.getOtpCode().equals(request.getOtpCode())) {
            return ResponseEntity.badRequest().body(new BaseResponse("Kode OTP salah!"));
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now(ZoneOffset.UTC).isAfter(user.getOtpExpiry())) {
            return ResponseEntity.badRequest().body(new BaseResponse("Kode OTP sudah kedaluwarsa!"));
        }

        user.setPass(passwordEncoder.encode(newPassword));
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        repo.save(user);

        return ResponseEntity.ok(new BaseResponse("Password berhasil diubah!"));
    }

    @Override
    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) {
        return ResponseEntity.badRequest().body(new BaseResponse("Gunakan endpoint OTP."));
    }
}