package com.siklus.application.repository;

import com.siklus.application.model.Sampah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SampahRepository extends JpaRepository<Sampah, Long> {
    @Query("""
        SELECT 
            s.rwSampah,
            SUM(CASE WHEN s.jnsSampah = 'Organik' THEN s.brtSampah ELSE 0 END),
            SUM(CASE WHEN s.jnsSampah = 'Anorganik' THEN s.brtSampah ELSE 0 END),
            SUM(CASE WHEN s.jnsSampah = 'Residu' THEN s.brtSampah ELSE 0 END)
        FROM Sampah s
        WHERE s.dateSampah >= :startDate
        GROUP BY s.rwSampah
        ORDER BY s.rwSampah
        """)
    List<Object[]> getChartRWMultiLine(LocalDate startDate);

    @Query("""
            SELECT s.jnsSampah, SUM(s.brtSampah)
            FROM Sampah s
            GROUP BY s.jnsSampah
            """)
    List<Object[]> getTotalSampahPerJenis();

    @Query("""
        SELECT 
            s.rwSampah,
            s.dateSampah,
            SUM(CASE WHEN s.jnsSampah = 'Organik' THEN s.brtSampah ELSE 0 END),
            SUM(CASE WHEN s.jnsSampah = 'Anorganik' THEN s.brtSampah ELSE 0 END),
            SUM(CASE WHEN s.jnsSampah = 'Residu' THEN s.brtSampah ELSE 0 END)
        FROM Sampah s
        WHERE s.dateSampah >= :startDate
        GROUP BY s.rwSampah, s.dateSampah
        ORDER BY s.rwSampah, s.dateSampah
        """)
    List<Object[]> getChartPerRwTanggal(LocalDate startDate);

    @Query("""
    SELECT
        s.rwSampah,
        YEAR(s.dateSampah),
        MONTH(s.dateSampah),

        SUM(CASE WHEN s.jnsSampah = 'Organik' THEN s.brtSampah ELSE 0 END),
        SUM(CASE WHEN s.jnsSampah = 'Anorganik' THEN s.brtSampah ELSE 0 END),
        SUM(CASE WHEN s.jnsSampah = 'Residu' THEN s.brtSampah ELSE 0 END)

    FROM Sampah s
    GROUP BY
        s.rwSampah,
        YEAR(s.dateSampah),
        MONTH(s.dateSampah)

    ORDER BY
        YEAR(s.dateSampah),
        MONTH(s.dateSampah)
""")
    List<Object[]> getChartPerRwBulanan();
}
