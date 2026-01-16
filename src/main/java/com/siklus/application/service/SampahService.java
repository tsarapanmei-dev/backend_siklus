package com.siklus.application.service;

import com.siklus.application.SampahReq;
import com.siklus.application.model.Sampah;

public interface SampahService {

    Sampah saveSampah(SampahReq request);
}
