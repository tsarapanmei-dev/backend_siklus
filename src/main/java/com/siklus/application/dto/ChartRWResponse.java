package com.siklus.application.dto;

public class ChartRWResponse {

    private String rw;
    private Double organik;
    private Double anorganik;
    private Double residu;

    public ChartRWResponse(String rw, Double organik, Double anorganik, Double residu) {
        this.rw = rw;
        this.organik = organik;
        this.anorganik = anorganik;
        this.residu = residu;
    }

    public String getRw() { return rw; }
    public Double getOrganik() { return organik; }
    public Double getAnorganik() { return anorganik; }
    public Double getResidu() { return residu; }
}