package com.siklus.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class SampahReq {

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("date_sampah")
    private LocalDate dateSampah;

    @JsonProperty("jns_sampah")
    private String jnsSampah;

    @JsonProperty("brt_sampah")
    private Integer brtSampah;

    // ===== GETTER SETTER =====

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateSampah() {
        return dateSampah;
    }

    public void setDateSampah(LocalDate dateSampah) {
        this.dateSampah = dateSampah;
    }

    public String getJnsSampah() {
        return jnsSampah;
    }

    public void setJnsSampah(String jnsSampah) {
        this.jnsSampah = jnsSampah;
    }

    public Integer getBrtSampah() {
        return brtSampah;
    }

    public void setBrtSampah(Integer brtSampah) {
        this.brtSampah = brtSampah;
    }
}