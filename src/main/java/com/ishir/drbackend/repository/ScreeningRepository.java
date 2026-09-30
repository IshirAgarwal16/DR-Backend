package com.ishir.drbackend.repository;

import com.ishir.drbackend.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByPatientId(Long patientId);

    @Transactional
    void deleteByPatientId(Long patientId);
}