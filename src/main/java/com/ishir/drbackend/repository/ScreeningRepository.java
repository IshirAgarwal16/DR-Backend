package com.ishir.drbackend.repository;

import com.ishir.drbackend.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByPatientId(Long patientId);
}