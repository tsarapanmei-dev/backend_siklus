package com.siklus.application.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${mail.from}")
    private String fromEmail;

    private final OkHttpClient client = new OkHttpClient();

    public void sendOtp(String toEmail, String otpCode, String purpose) {
        String jsonBody = """
                {
                  "sender": { "email": "%s", "name": "Siklus App" },
                  "to": [{ "email": "%s" }],
                  "subject": "Kode OTP Siklus - %s",
                  "textContent": "Halo!\\n\\nKode OTP kamu untuk %s adalah:\\n\\n  %s\\n\\nKode ini berlaku selama 5 menit.\\nJangan bagikan kode ini kepada siapapun.\\n\\nTim Siklus"
                }
                """.formatted(fromEmail, toEmail, purpose, purpose, otpCode);

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.brevo.com/v3/smtp/email")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("api-key", apiKey)
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "null";
                throw new RuntimeException("Brevo API error: " + response.code() + " - " + responseBody);
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengirim email via Brevo: " + e.getMessage(), e);
        }
    }
}