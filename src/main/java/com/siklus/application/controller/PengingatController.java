package com.siklus.application.controller;

import com.siklus.application.dto.PengingatReq;
import com.siklus.application.model.Pengingat;
import com.siklus.application.service.PengingatService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pengingat")
@CrossOrigin
public class PengingatController {

    private final PengingatService pengingatService;

    public PengingatController(PengingatService pengingatService) {
        this.pengingatService = pengingatService;
    }

    @PostMapping("/save")
    public Pengingat save(@RequestBody PengingatReq req) {
        return pengingatService.savePengingat(req);
    }

    @GetMapping("/user/{id}")
    public List<Pengingat> getByUser(@PathVariable Long id) {
        return pengingatService.getByUser(id);
    }
}