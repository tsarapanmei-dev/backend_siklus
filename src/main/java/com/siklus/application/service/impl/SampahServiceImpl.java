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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

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

    private Sampah.RWSampah parseRW(Object rwObj, int rowIndex) {
        if (rwObj == null) {
            throw new RuntimeException(
                    "RW bernilai null pada baris ke-" + rowIndex
            );
        }
        if (rwObj instanceof Sampah.RWSampah) {
            return (Sampah.RWSampah) rwObj;
        }
        if (rwObj instanceof String) {
            return Sampah.RWSampah.fromDbValue((String) rwObj);
        }
        throw new RuntimeException(
                "Tipe RW tidak dikenali pada baris ke-" + rowIndex
                        + ": " + rwObj.getClass().getName()
        );
    }

    private LocalDate parseLocalDate(Object dateObj, int rowIndex) {
        if (dateObj == null) {
            throw new RuntimeException(
                    "Tanggal bernilai null pada baris ke-" + rowIndex
            );
        }
        if (dateObj instanceof LocalDate) {
            return (LocalDate) dateObj;
        }
        if (dateObj instanceof java.sql.Date) {
            return ((java.sql.Date) dateObj).toLocalDate();
        }
        throw new RuntimeException(
                "Tipe tanggal tidak dikenali pada baris ke-" + rowIndex
                        + ": " + dateObj.getClass().getName()
        );
    }

    private double parseDouble(Object numObj, String fieldName, int rowIndex) {
        if (numObj == null) {
            return 0.0;
        }
        return ((Number) numObj).doubleValue();
    }

    @Override
    public Sampah saveSampah(SampahReq request) {
        try {
            User user = userRepository.findById(request.getIdUser())
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

            Sampah sampah = new Sampah();
            sampah.setDateSampah(LocalDate.parse(request.getDateSampah()));
            sampah.setJnsSampah(Sampah.JenisSampah.valueOf(request.getJnsSampah()));
            sampah.setBrtSampah(request.getBrtSampah());
            sampah.setRwSampah(
                    Sampah.RWSampah.fromDbValue(user.getRwUser())
            );

            return sampahRepository.save(sampah);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ChartRWResponse> getChartRW(ChartFilterType filter) {
        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartRWMultiLine(startDate);
        List<ChartRWResponse> chart = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            Object[] row = result.get(i);

            Sampah.RWSampah rwEnum = parseRW(row[0], i);
            String rw              = rwEnum.getDbValue();
            double organik         = parseDouble(row[1], "organik",   i);
            double anorganik       = parseDouble(row[2], "anorganik", i);
            double residu          = parseDouble(row[3], "residu",    i);

            chart.add(new ChartRWResponse(rw, organik, anorganik, residu));
        }
        return chart;
    }

    @Override
    public List<ChartRwTanggalResponse> getChartRwTanggal(ChartFilterType filter) {
        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartPerRwTanggal(startDate);
        List<ChartRwTanggalResponse> chart = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            Object[] row = result.get(i);

            Sampah.RWSampah rwEnum = parseRW(row[0], i);
            String rw              = rwEnum.getDbValue();
            LocalDate tanggal      = parseLocalDate(row[1], i);
            double organik         = parseDouble(row[2], "organik",   i);
            double anorganik       = parseDouble(row[3], "anorganik", i);
            double residu          = parseDouble(row[4], "residu",    i);

            chart.add(new ChartRwTanggalResponse(rw, tanggal, organik, anorganik, residu));
        }
        return chart;
    }

    @Override
    public List<ChartRwBulananResponse> getChartRwBulanan() {
        List<Object[]> result = sampahRepository.getChartPerRwBulanan();
        List<ChartRwBulananResponse> chart = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            Object[] row = result.get(i);

            Sampah.RWSampah rwEnum = parseRW(row[0], i);
            String rw              = rwEnum.getDbValue();
            int year               = ((Number) row[1]).intValue();
            int month              = ((Number) row[2]).intValue();
            String bulan           = month + "/" + year;
            double organik         = parseDouble(row[3], "organik",   i);
            double anorganik       = parseDouble(row[4], "anorganik", i);
            double residu          = parseDouble(row[5], "residu",    i);

            chart.add(new ChartRwBulananResponse(rw, bulan, organik, anorganik, residu));
        }
        return chart;
    }

    @Override
    public List<ChartJenisResponse> getChartJenis() {
        List<Object[]> result = sampahRepository.getTotalSampahPerJenis();
        List<ChartJenisResponse> chart = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            Object[] row = result.get(i);
            String jenis = row[0] != null ? row[0].toString() : "UNKNOWN";
            double total = parseDouble(row[1], "total", i);
            chart.add(new ChartJenisResponse(jenis, total));
        }
        return chart;
    }

    @Override
    public byte[] exportExcel(ChartFilterType filter) throws Exception {
        LocalDate startDate = resolveStartDate(filter);
        List<Object[]> result = sampahRepository.getChartPerRwTanggal(startDate);

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);

        try {
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
            for (int i = 0; i < 5; i++) {
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (int i = 0; i < result.size(); i++) {
                Object[] rowData = result.get(i);

                Sampah.RWSampah rwEnum = parseRW(rowData[0], i);
                String rw              = rwEnum.getDbValue();
                LocalDate tanggal      = parseLocalDate(rowData[1], i);
                double organik         = parseDouble(rowData[2], "organik",   i);
                double anorganik       = parseDouble(rowData[3], "anorganik", i);
                double residu          = parseDouble(rowData[4], "residu",    i);

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rw);
                row.createCell(1).setCellValue(tanggal.toString());
                row.createCell(2).setCellValue(organik);
                row.createCell(3).setCellValue(anorganik);
                row.createCell(4).setCellValue(residu);
            }

            sheet.setColumnWidth(0, 3500);
            sheet.setColumnWidth(1, 4500);
            sheet.setColumnWidth(2, 3500);
            sheet.setColumnWidth(3, 3500);
            sheet.setColumnWidth(4, 3500);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw e;

        } finally {
            workbook.close();
            workbook.dispose();
        }
    }

    private LocalDate resolveStartDate(ChartFilterType filter) {
        LocalDate now = LocalDate.now();
        return switch (filter) {
            case DAYS_7   -> now.minusDays(7);
            case DAYS_30  -> now.minusDays(30);
            case DAYS_90  -> now.minusDays(90);
            case DAYS_365 -> now.minusDays(365);
        };
    }
}