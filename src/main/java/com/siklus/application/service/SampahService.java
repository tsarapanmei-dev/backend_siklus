package com.siklus.application.service;

import com.siklus.application.SampahReq;
import com.siklus.application.dto.ChartFilterType;
import com.siklus.application.dto.ChartJenisResponse;
import com.siklus.application.dto.ChartRWResponse;
import com.siklus.application.dto.ChartRwBulananResponse;
import com.siklus.application.dto.ChartRwTanggalResponse;
import com.siklus.application.model.Sampah;

import java.util.List;

public interface SampahService {

    Sampah saveSampah(SampahReq request);
    List<ChartRWResponse> getChartRW(ChartFilterType filter);
    List<ChartRwTanggalResponse> getChartRwTanggal(ChartFilterType filter);
    List<ChartRwBulananResponse> getChartRwBulanan();
    List<ChartJenisResponse> getChartJenis();
    byte[] exportExcel(ChartFilterType filter, Sampah.RWSampah rw) throws Exception;
}