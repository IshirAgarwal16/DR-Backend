package com.ishir.drbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private Integer drGrade;

    private String drClass;

    private Double confidence;

    private String referableStatus;

    private Double referableProbability;

    private String recommendation;

    private Double sharpness;

    private Double brightness;

    private Double contrast;

    /*
     * Grad-CAM explanation image
     */
    @Column(columnDefinition = "TEXT")
    private String gradcamImage;

    /*
     * Original fundus image
     */
    @Column(columnDefinition = "TEXT")
    private String originalImage;


    // =========================
    // Default Constructor
    // =========================

    public Screening() {
    }


    // =========================
    // Parameterized Constructor
    // =========================

    public Screening(
            Long patientId,
            Integer drGrade,
            String drClass,
            Double confidence,
            String referableStatus,
            Double referableProbability,
            String recommendation,
            Double sharpness,
            Double brightness,
            Double contrast,
            String gradcamImage,
            String originalImage
    ) {

        this.patientId = patientId;
        this.drGrade = drGrade;
        this.drClass = drClass;
        this.confidence = confidence;
        this.referableStatus = referableStatus;
        this.referableProbability = referableProbability;
        this.recommendation = recommendation;
        this.sharpness = sharpness;
        this.brightness = brightness;
        this.contrast = contrast;
        this.gradcamImage = gradcamImage;
        this.originalImage = originalImage;
    }


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }


    public Integer getDrGrade() {
        return drGrade;
    }

    public void setDrGrade(Integer drGrade) {
        this.drGrade = drGrade;
    }


    public String getDrClass() {
        return drClass;
    }

    public void setDrClass(String drClass) {
        this.drClass = drClass;
    }


    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }


    public String getReferableStatus() {
        return referableStatus;
    }

    public void setReferableStatus(String referableStatus) {
        this.referableStatus = referableStatus;
    }


    public Double getReferableProbability() {
        return referableProbability;
    }

    public void setReferableProbability(
            Double referableProbability
    ) {
        this.referableProbability = referableProbability;
    }


    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }


    public Double getSharpness() {
        return sharpness;
    }

    public void setSharpness(Double sharpness) {
        this.sharpness = sharpness;
    }


    public Double getBrightness() {
        return brightness;
    }

    public void setBrightness(Double brightness) {
        this.brightness = brightness;
    }


    public Double getContrast() {
        return contrast;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }


    public String getGradcamImage() {
        return gradcamImage;
    }

    public void setGradcamImage(String gradcamImage) {
        this.gradcamImage = gradcamImage;
    }


    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

}