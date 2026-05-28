package com.siklus.application;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String email_user;

    @NotBlank
    private String pass;

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}