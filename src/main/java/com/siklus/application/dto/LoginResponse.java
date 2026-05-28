package com.siklus.application.dto;

public class LoginResponse {
    private Long id_user;
    private String email_user;
    private String rw_user;

    public LoginResponse(Long id_user, String email_user, String rw_user){
        this.id_user = id_user;
        this.email_user = email_user;
        this.rw_user = rw_user;
    }

    public Long getId_user() {
        return id_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public String getRw_user() {
        return rw_user;
    }
}
