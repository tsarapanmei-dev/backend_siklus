package com.siklus.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sampah")
public class Sampah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sampah")
    private Long idSampah;

    @Column(name = "date_sampah")
    private LocalDate dateSampah;

    @Column(name = "jns_sampah", nullable = false)
    private String jnsSampah;

    @Column(name = "brt_sampah", nullable = false)
    private Integer brtSampah;

    @Column(name = "rw_sampah", nullable = false)
    private String rwSampah;

    public Long getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(Long idSampah) {
        this.idSampah = idSampah;
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

    public String getRwSampah() {
        return rwSampah;
    }

    public void setRwSampah(String rwSampah) {
        this.rwSampah = rwSampah;
    }
}

