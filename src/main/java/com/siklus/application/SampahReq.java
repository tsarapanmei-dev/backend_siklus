package com.siklus.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SampahReq {

    @JsonProperty("id_user")
    @NotNull
    private Long idUser;

    @JsonProperty("date_sampah")
    @NotBlank
    private String dateSampah;

    @JsonProperty("jns_sampah")
    @NotBlank
    private String jnsSampah;

    @JsonProperty("brt_sampah")
    @NotNull(message = "Berat sampah wajib diisi")
    @DecimalMin(value = "0.01", message = "Berat sampah harus lebih dari 0 kg")
    @Digits(integer = 6, fraction = 2, message = "Berat sampah maksimal 2 angka di belakang koma")
    private Double brtSampah;

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }

    public String getDateSampah() { return dateSampah; }
    public void setDateSampah(String dateSampah) { this.dateSampah = dateSampah; }

    public String getJnsSampah() { return jnsSampah; }
    public void setJnsSampah(String jnsSampah) { this.jnsSampah = jnsSampah; }

    public Double getBrtSampah() { return brtSampah; }
    public void setBrtSampah(Double brtSampah) { this.brtSampah = brtSampah; }
}