package com.momhelp.service;

import com.momhelp.dto.VegetableRequestDTO;
import com.momhelp.dto.VegetableResponseDTO;
import java.util.List;

public interface VegetableService {
    
    // Add new vegetable
    VegetableResponseDTO addVegetable(VegetableRequestDTO vegetableRequestDTO);
    
    // Update existing vegetable
    VegetableResponseDTO updateVegetable(Long id, VegetableRequestDTO vegetableRequestDTO);
    
    // Delete vegetable by ID
    void deleteVegetable(Long id);
    
    // Get vegetable by ID
    VegetableResponseDTO getVegetableById(Long id);
    
    // Get all vegetables
    List<VegetableResponseDTO> getAllVegetables();
    
    // Search vegetables by name
    List<VegetableResponseDTO> searchVegetablesByName(String name);
    
    // Get available vegetables (not expired)
    List<VegetableResponseDTO> getAvailableVegetables();
    
    // Get spoiled vegetables
    List<VegetableResponseDTO> getSpoiledVegetables();
    
    // Mark vegetable as spoiled
    VegetableResponseDTO markAsSpoiled(Long id);
    
    // Remove spoiled vegetable
    void removeSpoiledVegetable(Long id);
    
 // Add this method signature
    VegetableResponseDTO useVegetable(Long id, Double weightUsed);
}