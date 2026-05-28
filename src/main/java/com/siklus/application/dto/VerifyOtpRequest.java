package com.siklus.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyOtpRequest {

    @JsonProperty("email_user")
    private String emailUser;

    @JsonProperty("otp_code")
    private String otpCode;

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
}