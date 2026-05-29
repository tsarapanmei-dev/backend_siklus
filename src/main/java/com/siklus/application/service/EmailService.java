package com.siklus.application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {this.mailSender = mailSender;}

    public void sendOtp(String toEmail, String otpCode, String purpose) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(
                    "siklusapplication@gmail.com",
                    "Siklus Application"
            );

            helper.setTo(toEmail);
            helper.setSubject("Kode OTP Siklus - " + purpose);
            String htmlContent =
                    """
                    <div style="
                        font-family: Arial, sans-serif;
                        padding: 20px;
                        color: #333333;
                    ">

                        <h2 style="color:#2E7D32;">
                            Siklus Application
                        </h2>

                        <p>Halo!</p>

                        <p>
                            Kode OTP kamu untuk
                            <b>%s</b>
                            adalah:
                        </p>

                        <div style="
                            background-color:#F1F8E9;
                            padding:15px;
                            border-radius:10px;
                            text-align:center;
                            margin:20px 0;
                        ">
                            <h1 style="
                                letter-spacing:5px;
                                color:#1B5E20;
                                margin:0;
                            ">
                                %s
                            </h1>
                        </div>

                        <p>
                            Kode ini berlaku selama
                            <b>5 menit</b>.
                        </p>

                        <p>
                            Jangan bagikan kode ini kepada siapa pun.
                        </p>

                        <br>

                        <p>
                            Salam,<br>
                            <b>Tim Siklus Application</b>
                        </p>

                    </div>
                    """.formatted(purpose, otpCode);

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Gagal mengirim email OTP", e);
        }
    }
}