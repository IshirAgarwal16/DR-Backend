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

    @Column(columnDefinition = "TEXT")
    private String gradcamImage;

    public Screening() {
    }

    public Screening(
            Long patientId,
            Integer drGrade,
            String drClass,
            Double confidence,
            String referableStatus,
            Double referableProbability,
            String recommendation,
            String gradcamImage
    ) {
        this.patientId = patientId;
        this.drGrade = drGrade;
        this.drClass = drClass;
        this.confidence = confidence;
        this.referableStatus = referableStatus;
        this.referableProbability = referableProbability;
        this.recommendation = recommendation;
        this.gradcamImage = gradcamImage;
    }

    public Long getId() {
        return id;
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

    public void setReferableProbability(Double referableProbability) {
        this.referableProbability = referableProbability;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getGradcamImage() {
        return gradcamImage;
    }

    public void setGradcamImage(String gradcamImage) {
        this.gradcamImage = gradcamImage;
    }
}