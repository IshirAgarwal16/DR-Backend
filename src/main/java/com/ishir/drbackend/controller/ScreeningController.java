package com.ishir.drbackend.controller;

import com.ishir.drbackend.model.Screening;
import com.ishir.drbackend.repository.ScreeningRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningRepository screeningRepository;

    public ScreeningController(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @PostMapping
    public Screening createScreening(@RequestBody Screening screening) {
        return screeningRepository.save(screening);
    }

    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(
            @PathVariable Long id) {

        return screeningRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public List<Screening> getPatientScreenings(
            @PathVariable Long patientId) {

        return screeningRepository.findByPatientId(patientId);
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<?> deletePatientScreenings(
            @PathVariable Long patientId) {

        List<Screening> screenings =
                screeningRepository.findByPatientId(patientId);

        if (screenings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        screeningRepository.deleteByPatientId(patientId);

        return ResponseEntity.ok(
                "Screening history deleted successfully"
        );
    }
}