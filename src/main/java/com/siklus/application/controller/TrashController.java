package com.siklus.application.controller;

import com.siklus.application.SampahReq;
import com.siklus.application.service.SampahService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sampah")
public class TrashController {

    private final SampahService sampahService;

    public TrashController(SampahService sampahService) {
        this.sampahService = sampahService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveSampah(@RequestBody SampahReq request) {
        return ResponseEntity.ok(sampahService.saveSampah(request));
    }
}

