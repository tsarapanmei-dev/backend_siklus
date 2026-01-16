package com.siklus.application.repository;

import com.siklus.application.model.Sampah;
import com.siklus.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampahRepository extends JpaRepository<Sampah, Long> {
}
