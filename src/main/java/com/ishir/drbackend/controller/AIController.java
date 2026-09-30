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
import java.util.Base64;

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


        // ============================================
        // SEND IMAGE TO FASTAPI
        // ============================================

        AIResponse result = aiService.analyzeImage(
                file.getBytes(),
                file.getOriginalFilename()
        );


        // ============================================
        // IF AI ANALYSIS FAILED
        // ============================================

        if (!result.isSuccess()) {
            return ResponseEntity.ok(result);
        }


        // ============================================
        // GET QUALITY DETAILS
        // ============================================

        AIResponse.QualityDetails qualityDetails =
                result.getQualityDetails();


        // ============================================
        // CONVERT ORIGINAL IMAGE TO BASE64
        // ============================================

        String contentType = file.getContentType();

        if (contentType == null) {
            contentType = "image/jpeg";
        }


        String originalImage =
                "data:" +
                        contentType +
                        ";base64," +
                        Base64.getEncoder().encodeToString(
                                file.getBytes()
                        );


        // ============================================
        // CREATE SCREENING RECORD
        // ============================================

        Screening screening = new Screening(

                patientId,

                result.getDrGrade(),

                result.getDrClass(),

                result.getConfidence(),

                result.getReferableStatus(),

                result.getReferableProbability(),

                result.getRecommendation(),

                qualityDetails != null
                        ? qualityDetails.getSharpness()
                        : null,

                qualityDetails != null
                        ? qualityDetails.getBrightness()
                        : null,

                qualityDetails != null
                        ? qualityDetails.getContrast()
                        : null,

                result.getGradcamImage(),

                originalImage
        );


        // ============================================
        // SAVE SCREENING TO POSTGRESQL
        // ============================================

        screeningRepository.save(screening);


        // ============================================
        // RETURN AI RESULT
        // ============================================

        return ResponseEntity.ok(result);

    }

}