package com.kunnect.KUnnect.domain;

import jakarta.persistence.*;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long univId;

    private String continent;
    private String country;
    private String univName;
    private String language;
    private Long quota;
    private String duration;
    private Integer minCompletedSemesters;
    private Double minGpa;
    private String languageRequirements;
    private String semesterSchedule;
    private String additionalInfo;
    private Double latitude;
    private Double longitude;


    public Long getUnivId() {
        return univId;
    }

    public void setUnivId(Long univId) {
        this.univId = univId;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnivName() {
        return univName;
    }

    public void setUnivName(String univName) {
        this.univName = univName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getQuota() {
        return quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getMinCompletedSemesters() {
        return minCompletedSemesters;
    }

    public void setMinCompletedSemesters(Integer minCompletedSemesters) {
        this.minCompletedSemesters = minCompletedSemesters;
    }

    public Double getMinGpa() {
        return minGpa;
    }

    public void setMinGpa(Double minGpa) {
        this.minGpa = minGpa;
    }

    public String getLanguageRequirements() {
        return languageRequirements;
    }

    public void setLanguageRequirements(String languageRequirements) {
        this.languageRequirements = languageRequirements;
    }

    public String getSemesterSchedule() {
        return semesterSchedule;
    }

    public void setSemesterSchedule(String semesterSchedule) {
        this.semesterSchedule = semesterSchedule;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
