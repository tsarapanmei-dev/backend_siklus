package com.siklus.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_user")
    @Column(name = "id_user")
    private Long idUser;

    @JsonProperty("email_user")
    @Column(name = "email_user", nullable = false, length = 255, unique = true)
    private String emailUser;

    @JsonProperty("rw_user")
    @Column(name = "rw_user", nullable = false, length = 10)
    private String rwUser;

    @JsonProperty("pass")
    @Column(name = "pass", nullable = false, length = 255)
    private String pass;

    @JsonProperty("is_verified")
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @JsonProperty("otp_code")
    @Column(name = "otp_code", length = 6)
    private String otpCode;

    @JsonProperty("otp_expiry")
    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getRwUser() {
        return rwUser;
    }

    public void setRwUser(String rwUser) {
        this.rwUser = rwUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }
}
