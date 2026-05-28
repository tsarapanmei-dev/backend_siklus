package com.siklus.application.repository;

import com.siklus.application.model.Pengingat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PengingatRepository extends JpaRepository<Pengingat, Long> {

    List<Pengingat> findByIdUser(Long idUser);

}
