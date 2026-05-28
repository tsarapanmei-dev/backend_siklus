package com.siklus.application.dto;

public class ChartRwBulananResponse {

    private String rw;
    private String bulan;

    private Double organik;
    private Double anorganik;
    private Double residu;

    public ChartRwBulananResponse(
            String rw,
            String bulan,
            Double organik,
            Double anorganik,
            Double residu
    ) {
        this.rw = rw;
        this.bulan = bulan;
        this.organik = organik;
        this.anorganik = anorganik;
        this.residu = residu;
    }

    public String getRw() {
        return rw;
    }

    public String getBulan() {
        return bulan;
    }

    public Double getOrganik() {
        return organik;
    }

    public Double getAnorganik() {
        return anorganik;
    }

    public Double getResidu() {
        return residu;
    }
}