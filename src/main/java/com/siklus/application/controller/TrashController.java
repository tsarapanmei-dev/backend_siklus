package com.siklus.application.controller;

import com.siklus.application.SampahReq;
import com.siklus.application.dto.*;
import com.siklus.application.service.SampahService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sampah")
public class TrashController {

    private final SampahService sampahService;

    public TrashController(SampahService sampahService) {
        this.sampahService = sampahService;
    }

    @PostMapping("/save")
    public ResponseEntity<java.util.Map<String, Object>> saveSampah(@Valid @RequestBody SampahReq request) {
        sampahService.saveSampah(request);
        return ResponseEntity.ok(
                java.util.Map.of(
                        "success", true,
                        "message", "Data sampah berhasil disimpan"
                ));
    }
    @GetMapping("/chart-rw")
    public List<ChartRWResponse> getChartRW(@RequestParam(defaultValue = "DAYS_90") ChartFilterType filter
    ) {
        return sampahService.getChartRW(filter);
    }
    @GetMapping("/chart-jenis")
    public List<ChartJenisResponse> getChartJenis() {
        return sampahService.getChartJenis();
    }
    @GetMapping("/chart-rw-tanggal")
    public List<ChartRwTanggalResponse> getChartRwTanggal(@RequestParam(defaultValue = "DAYS_90") ChartFilterType filter) {
        return sampahService.getChartRwTanggal(filter);
    }
    @GetMapping("/chart-rw-bulanan")
    public List<ChartRwBulananResponse> getChartRwBulanan() {return sampahService.getChartRwBulanan();}

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(@RequestParam(defaultValue = "DAYS_90") ChartFilterType filter
    ) {
        try {
            byte[] excelData = sampahService.exportExcel(filter);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data_sampah.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excelData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

