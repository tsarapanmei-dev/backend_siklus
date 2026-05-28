package com.siklus.application.service.impl;

import com.siklus.application.SampahReq;
import com.siklus.application.dto.ChartJenisResponse;
import com.siklus.application.dto.ChartRWResponse;
import com.siklus.application.dto.ChartRwTanggalResponse;
import com.siklus.application.dto.ChartRwBulananResponse;
import com.siklus.application.dto.ChartFilterType;
import com.siklus.application.model.Sampah;
import com.siklus.application.model.User;
import com.siklus.application.repository.SampahRepository;
import com.siklus.application.repository.UserRepository;
import com.siklus.application.service.SampahService;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SampahServiceImpl implements SampahService {
    private final SampahRepository sampahRepository;
    private final UserRepository userRepository;

    public SampahServiceImpl(
            SampahRepository sampahRepository,
            UserRepository userRepository
    ) {
        this.sampahRepository = sampahRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Sampah saveSampah(SampahReq request) {
        try {
            System.out.println("===== DEBUG SAVE SAMPAH =====");
            System.out.println("ID USER      = " + request.getIdUser());
            System.out.println("DATE SAMPAH  = " + request.getDateSampah());
            System.out.println("JENIS SAMPAH = " + request.getJnsSampah());
            System.out.println("BERAT SAMPAH = " + request.getBrtSampah());

            User user = userRepository.findById(request.getIdUser())
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

            System.out.println("RW USER DB   = " + user.getRwUser());

            Sampah sampah = new Sampah();
            sampah.setDateSampah(LocalDate.parse(request.getDateSampah()));
            sampah.setJnsSampah(Sampah.JenisSampah.valueOf(request.getJnsSampah()));
            sampah.setBrtSampah(request.getBrtSampah());

            System.out.println("RW USER FINAL = " + user.getRwUser());

            sampah.setRwSampah(
                    Sampah.RWSampah.fromDbValue(user.getRwUser())
            );

            System.out.println("BEFORE SAVE");

            Sampah saved = sampahRepository.save(sampah);

            System.out.println("AFTER SAVE");
            return saved;

        } catch (Exception e) {
            System.out.println("===== ERROR SAVE SAMPAH =====");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<ChartRWResponse> getChartRW(
            ChartFilterType filter
    ) {
        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartRWMultiLine(startDate);
        List<ChartRWResponse> chart = new ArrayList<>();

        for (Object[] row : result) {
            Sampah.RWSampah rwEnum = (Sampah.RWSampah) row[0];
            String rw = rwEnum.getDbValue();
            Double organik = ((Number) row[1]).doubleValue();
            Double anorganik = ((Number) row[2]).doubleValue();
            Double residu = ((Number) row[3]).doubleValue();

            chart.add(
                    new ChartRWResponse(
                            rw,
                            organik,
                            anorganik,
                            residu
                    )
            );
        }
        return chart;
    }

    @Override
    public List<ChartRwTanggalResponse> getChartRwTanggal(ChartFilterType filter) {

        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartPerRwTanggal(startDate);
        List<ChartRwTanggalResponse> chart = new ArrayList<>();

        for (Object[] row : result) {
            Sampah.RWSampah rwEnum = (Sampah.RWSampah) row[0];
            String rw = rwEnum.getDbValue();
            LocalDate tanggal = (LocalDate) row[1];
            Double organik = ((Number) row[2]).doubleValue();
            Double anorganik = ((Number) row[3]).doubleValue();
            Double residu = ((Number) row[4]).doubleValue();

            chart.add(
                    new ChartRwTanggalResponse(
                            rw,
                            tanggal,
                            organik,
                            anorganik,
                            residu
                    )
            );
        }
        return chart;
    }

    @Override
    public List<ChartRwBulananResponse> getChartRwBulanan() {
        List<Object[]> result = sampahRepository.getChartPerRwBulanan();
        List<ChartRwBulananResponse> chart = new ArrayList<>();

        for (Object[] row : result) {
            Sampah.RWSampah rwEnum = (Sampah.RWSampah) row[0];
            String rw = rwEnum.getDbValue();
            Integer year = ((Number) row[1]).intValue();
            Integer month = ((Number) row[2]).intValue();
            String bulan = month + "/" + year;
            Double organik = ((Number) row[3]).doubleValue();
            Double anorganik = ((Number) row[4]).doubleValue();
            Double residu = ((Number) row[5]).doubleValue();
            chart.add(
                    new ChartRwBulananResponse(
                            rw,
                            bulan,
                            organik,
                            anorganik,
                            residu
                    )
            );
        }
        return chart;
    }

    @Override
    public List<ChartJenisResponse> getChartJenis() {

        List<Object[]> result = sampahRepository.getTotalSampahPerJenis();
        List<ChartJenisResponse> chart = new ArrayList<>();

        for (Object[] row : result) {
            String jenis = row[0].toString();
            Double total = ((Number) row[1]).doubleValue();

            chart.add(new ChartJenisResponse(jenis, total));
        }
        return chart;
    }

    private LocalDate resolveStartDate(ChartFilterType filter) {
        LocalDate now = LocalDate.now();
        return switch (filter) {
            case DAYS_7 -> now.minusDays(7);
            case DAYS_30 -> now.minusDays(30);
            case DAYS_90 -> now.minusDays(90);
            case DAYS_365 -> now.minusDays(365);
        };
    }

    @Override
    public byte[] exportExcel(ChartFilterType filter) throws Exception {
        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartPerRwTanggal(startDate);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data Sampah");

        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("RW");
        headerRow.createCell(1).setCellValue("Tanggal");
        headerRow.createCell(2).setCellValue("Organik");
        headerRow.createCell(3).setCellValue("Anorganik");
        headerRow.createCell(4).setCellValue("Residu");

        CellStyle headerStyle = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);

        headerStyle.setFont(font);

        for (int i = 0; i < 5; i++) {headerRow.getCell(i).setCellStyle(headerStyle);}

        int rowNum = 1;

        for (Object[] rowData : result) {
            Sampah.RWSampah rwEnum = (Sampah.RWSampah) rowData[0];
            String rw = rwEnum.getDbValue();

            LocalDate tanggal = (LocalDate) rowData[1];
            Double organik = ((Number) rowData[2]).doubleValue();
            Double anorganik = ((Number) rowData[3]).doubleValue();
            Double residu = ((Number) rowData[4]).doubleValue();

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(rw);
            row.createCell(1).setCellValue(tanggal.toString());
            row.createCell(2).setCellValue(organik);
            row.createCell(3).setCellValue(anorganik);
            row.createCell(4).setCellValue(residu);
        }

        for (int i = 0; i < 5; i++) {sheet.autoSizeColumn(i);}

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}

