package com.siklus.application.service.impl;

import com.siklus.application.dto.PengingatReq;
import com.siklus.application.exception.ResourceNotFoundException;
import com.siklus.application.model.Pengingat;
import com.siklus.application.model.TipePengingat;
import com.siklus.application.repository.PengingatRepository;
import com.siklus.application.service.PengingatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengingatServiceImpl implements PengingatService {

    private final PengingatRepository pengingatRepository;

    public PengingatServiceImpl(PengingatRepository pengingatRepository) {
        this.pengingatRepository = pengingatRepository;
    }

    @Override
    public Pengingat savePengingat(PengingatReq req) {

        boolean sudahAda = pengingatRepository.existsByIdUserAndTanggalMulai(
                req.getIdUser(),
                req.getTanggalMulai()
        );

        if (sudahAda) {
            throw new ResourceNotFoundException("Tanggal ini sudah pernah diinput. Silakan pilih tanggal lain.");
        }

        Pengingat pengingat = new Pengingat();

        pengingat.setIdUser(req.getIdUser());
        pengingat.setTanggalMulai(req.getTanggalMulai());
        pengingat.setNotifAktif(req.getNotifAktif());
        pengingat.setTipePengingat(
                req.getTipePengingat() != null ? req.getTipePengingat() : TipePengingat.HARI_TERTENTU
        );

        return pengingatRepository.save(pengingat);
    }

    @Override
    public List<Pengingat> getByUser(Long idUser) {
        return pengingatRepository.findByIdUser(idUser);
    }
}