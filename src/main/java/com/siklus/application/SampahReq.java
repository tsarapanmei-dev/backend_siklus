package com.siklus.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SampahReq {

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("date_sampah")
    private String dateSampah;

    @JsonProperty("jns_sampah")
    private String jnsSampah;

    @JsonProperty("brt_sampah")
    private Double brtSampah;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getDateSampah() {
        return dateSampah;
    }

    public void setDateSampah(String dateSampah) {
        this.dateSampah = dateSampah;
    }

    public String getJnsSampah() {
        return jnsSampah;
    }

    public void setJnsSampah(String jnsSampah) {
        this.jnsSampah = jnsSampah;
    }

    public Double getBrtSampah() {
        return brtSampah;
    }

    public void setBrtSampah(Double brtSampah) {
        this.brtSampah = brtSampah;
    }
}