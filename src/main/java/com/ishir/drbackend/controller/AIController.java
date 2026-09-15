package com.ishir.drbackend.controller;

import com.ishir.drbackend.dto.AIResponse;
import com.ishir.drbackend.model.Screening;
import com.ishir.drbackend.repository.ScreeningRepository;
import com.ishir.drbackend.service.AIService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;
    private final ScreeningRepository screeningRepository;

    public AIController(
            AIService aiService,
            ScreeningRepository screeningRepository
    ) {
        this.aiService = aiService;
        this.screeningRepository = screeningRepository;
    }

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> analyzeImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("patientId") Long patientId
    ) throws IOException {

        // Step 1: Send image to AI service
        AIResponse result = aiService.analyzeImage(
                file.getBytes(),
                file.getOriginalFilename()
        );

        // Step 2: If AI rejects the image, don't save screening
        if (!result.isSuccess()) {
            return ResponseEntity.ok(result);
        }

        // Step 3: Create Screening object
        Screening screening = new Screening(
                patientId,
                result.getDrGrade(),
                result.getDrClass(),
                result.getConfidence(),
                result.getReferableStatus(),
                result.getReferableProbability(),
                result.getRecommendation(),
                result.getGradcamImage()
        );

        // Step 4: Save screening to PostgreSQL
        Screening savedScreening =
                screeningRepository.save(screening);

        // Step 5: Return saved screening
        return ResponseEntity.ok(savedScreening);
    }
}