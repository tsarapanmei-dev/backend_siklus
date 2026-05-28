package com.siklus.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pengingat")
public class Pengingat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_pengingat")
    @Column(name = "id_pengingat")
    private Long idPengingat;

    @JsonProperty("id_user")
    @Column(name = "id_user")
    private Long idUser;

    @JsonProperty("tanggal_mulai")
    @Column(name = "tanggal_mulai")
    private LocalDate tanggalMulai;

    @JsonProperty("notif_aktif")
    @Column(name = "notif_aktif")
    private Boolean notifAktif;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getIdPengingat() {
        return idPengingat;
    }

    public Long getIdUser() {
        return idUser;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public Boolean getNotifAktif() {
        return notifAktif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setIdPengingat(Long idPengingat) {
        this.idPengingat = idPengingat;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public void setNotifAktif(Boolean notifAktif) {
        this.notifAktif = notifAktif;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}