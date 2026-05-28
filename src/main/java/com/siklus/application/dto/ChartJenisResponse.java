package com.siklus.application.dto;

public class ChartJenisResponse {

    private String jenis;
    private Double total;

    public ChartJenisResponse(String jenis, Double total) {
        this.jenis = jenis;
        this.total = total;
    }

    public String getJenis() {
        return jenis;
    }

    public Double getTotal() {
        return total;
    }
}
