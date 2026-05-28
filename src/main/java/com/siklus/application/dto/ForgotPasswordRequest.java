package com.siklus.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForgotPasswordRequest {

    @JsonProperty("email_user")
    private String emailUser;

    @JsonProperty("pass")
    private String pass;

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}