package com.siklus.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @JsonProperty("email_user")
    @NotBlank
    private String emailUser;

    @JsonProperty("rw_user")
    @NotBlank
    private String rwUser;

    @JsonProperty("pass")
    @NotBlank
    private String pass;

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public String getRwUser() { return rwUser; }
    public void setRwUser(String rwUser) { this.rwUser = rwUser; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
}