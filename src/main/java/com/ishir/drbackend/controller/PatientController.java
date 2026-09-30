package com.ishir.drbackend.controller;

import com.ishir.drbackend.model.Patient;
import com.ishir.drbackend.repository.PatientRepository;
import com.ishir.drbackend.repository.ScreeningRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final ScreeningRepository screeningRepository;

    public PatientController(
            PatientRepository patientRepository,
            ScreeningRepository screeningRepository
    ) {
        this.patientRepository = patientRepository;
        this.screeningRepository = screeningRepository;
    }

    // Create patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable Long id
    ) {

        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete patient and all screening history
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(
            @PathVariable Long id
    ) {

        // Check if patient exists
        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Delete all screening records of this patient
        screeningRepository.deleteByPatientId(id);

        // Delete the patient
        patientRepository.deleteById(id);

        return ResponseEntity.ok(
                "Patient and screening history deleted successfully"
        );
    }
}