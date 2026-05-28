package com.siklus.application.model;

import jakarta.persistence.*;

import java.time.LocalDate;

import com.siklus.application.converter.RWSampahConverter;

@Entity
@Table(name = "sampah")
public class Sampah {

    public enum JenisSampah {
        Organik, Anorganik, Residu
    }

    public enum RWSampah {

        RW_02("RW 02"),
        RW_14("RW 14"),
        RW_15("RW 15"),
        RW_17("RW 17");

        private final String dbValue;

        RWSampah(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static RWSampah fromDbValue(String value) {

            for (RWSampah rw : values()) {
                if (rw.dbValue.equals(value)) {
                    return rw;
                }
            }

            throw new IllegalArgumentException(
                    "RW tidak valid: " + value
            );
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sampah")
    private Long idSampah;

    @Column(name = "date_sampah")
    private LocalDate dateSampah;

    @Column(name = "jns_sampah", nullable = false)
    @Enumerated(EnumType.STRING)
    private JenisSampah jnsSampah;

    @Column(name = "brt_sampah", nullable = false)
    private Double brtSampah;

    @Column(name = "rw_sampah", nullable = false)
    @Convert(converter = RWSampahConverter.class)
    private RWSampah rwSampah;

    public Long getIdSampah() {
        return idSampah;
    }

    public void setIdSampah(Long idSampah) {
        this.idSampah = idSampah;
    }

    public LocalDate getDateSampah() {
        return dateSampah;
    }

    public void setDateSampah(LocalDate dateSampah) {
        this.dateSampah = dateSampah;
    }

    public JenisSampah getJnsSampah() {
        return jnsSampah;
    }

    public void setJnsSampah(JenisSampah jnsSampah) {
        this.jnsSampah = jnsSampah;
    }

    public RWSampah getRwSampah() {
        return rwSampah;
    }

    public void setRwSampah(RWSampah rwSampah) {
        this.rwSampah = rwSampah;
    }

    public Double getBrtSampah() {
        return brtSampah;
    }

    public void setBrtSampah(Double brtSampah) {
        this.brtSampah = brtSampah;
    }

}

