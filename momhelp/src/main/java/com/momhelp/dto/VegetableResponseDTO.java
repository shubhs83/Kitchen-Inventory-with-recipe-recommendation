package com.momhelp.dto;

import java.util.Date;

public class VegetableResponseDTO {
    private Long id;
    private String name;
    private Double weight;
    private String unit;
    private Date addedDate;
    private Date useBeforeDate;
    private boolean spoiled; // Changed from isSpoiled to spoiled
    
    // Constructors
    public VegetableResponseDTO() {}
    
    public VegetableResponseDTO(Long id, String name, Double weight, String unit, 
                               Date addedDate, Date useBeforeDate, boolean spoiled) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.unit = unit;
        this.addedDate = addedDate;
        this.useBeforeDate = useBeforeDate;
        this.spoiled = spoiled;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }
    
    public Date getUseBeforeDate() { return useBeforeDate; }
    public void setUseBeforeDate(Date useBeforeDate) { this.useBeforeDate = useBeforeDate; }
    
    public boolean isSpoiled() { return spoiled; } // Getter is isSpoiled()
    public void setSpoiled(boolean spoiled) { this.spoiled = spoiled; }
}