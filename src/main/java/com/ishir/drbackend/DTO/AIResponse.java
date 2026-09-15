package com.ishir.drbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AIResponse {

    private boolean success;
    private String stage;
    private boolean fundus;

    @JsonProperty("fundus_confidence")
    private Double fundusConfidence;

    private String quality;

    @JsonProperty("dr_grade")
    private Integer drGrade;

    @JsonProperty("dr_class")
    private String drClass;

    private Double confidence;

    @JsonProperty("referable_status")
    private String referableStatus;

    @JsonProperty("referable_probability")
    private Double referableProbability;

    private String recommendation;

    @JsonProperty("gradcam_class")
    private String gradcamClass;

    @JsonProperty("gradcam_image")
    private String gradcamImage;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public boolean isFundus() {
        return fundus;
    }

    public void setFundus(boolean fundus) {
        this.fundus = fundus;
    }

    public Double getFundusConfidence() {
        return fundusConfidence;
    }

    public void setFundusConfidence(Double fundusConfidence) {
        this.fundusConfidence = fundusConfidence;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
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

    public String getGradcamClass() {
        return gradcamClass;
    }

    public void setGradcamClass(String gradcamClass) {
        this.gradcamClass = gradcamClass;
    }

    public String getGradcamImage() {
        return gradcamImage;
    }

    public void setGradcamImage(String gradcamImage) {
        this.gradcamImage = gradcamImage;
    }
}