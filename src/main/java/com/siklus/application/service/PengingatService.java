package com.siklus.application.service;

import com.siklus.application.dto.PengingatReq;
import com.siklus.application.model.Pengingat;

import java.util.List;

public interface PengingatService {

    Pengingat savePengingat(PengingatReq req);

    List<Pengingat> getByUser(Long idUser);

}