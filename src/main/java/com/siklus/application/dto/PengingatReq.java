package com.siklus.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.siklus.application.model.TipePengingat;

import java.time.LocalDate;

public class PengingatReq {

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("tanggal_mulai")
    private LocalDate tanggalMulai;

    @JsonProperty("notif_aktif")
    private Boolean notifAktif;

    @JsonProperty("tipe_pengingat")
    private TipePengingat tipePengingat;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Boolean getNotifAktif() {
        return notifAktif;
    }

    public void setNotifAktif(Boolean notifAktif) {
        this.notifAktif = notifAktif;
    }

    public TipePengingat getTipePengingat() {
        return tipePengingat;
    }

    public void setTipePengingat(TipePengingat tipePengingat) {
        this.tipePengingat = tipePengingat;
    }
}