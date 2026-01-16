package com.siklus.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user") // kolom di database
    private Long idUser;

    @JsonProperty("email_user")
    @Column(name = "email_user", nullable = false, length = 25, unique = true)
    private String emailUser;

    @JsonProperty("rw_user")
    @Column(name = "rw_user", nullable = false, length = 10)
    private String rwUser;

    @JsonProperty("pass")
    @Column(name = "pass", nullable = false, length = 25)
    private String pass;

    // Getter dan Setter
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
}
