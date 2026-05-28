package com.siklus.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ChartRwTanggalResponse {

    private String rw;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal;

    private Double organik;
    private Double anorganik;
    private Double residu;

    public ChartRwTanggalResponse(
            String rw, LocalDate tanggal,
            Double organik, Double anorganik, Double residu
    ) {
        this.rw = rw;
        this.tanggal = tanggal;
        this.organik = organik;
        this.anorganik = anorganik;
        this.residu = residu;
    }

    public String getRw() { return rw; }
    public LocalDate getTanggal() { return tanggal; }
    public Double getOrganik() { return organik; }
    public Double getAnorganik() { return anorganik; }
    public Double getResidu() { return residu; }
}