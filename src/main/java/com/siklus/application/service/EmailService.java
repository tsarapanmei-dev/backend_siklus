package com.siklus.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String toEmail, String otpCode, String purpose) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(toEmail);

        message.setSubject("Kode OTP Siklus - " + purpose);

        message.setText(
                "Halo!\n\n" +
                        "Kode OTP kamu untuk " + purpose + " adalah:\n\n" +
                        "  " + otpCode + "\n\n" +
                        "Kode ini berlaku selama 5 menit.\n" +
                        "Jangan bagikan kode ini kepada siapapun.\n\n" +
                        "Tim Siklus"
        );

        mailSender.send(message);
    }
}