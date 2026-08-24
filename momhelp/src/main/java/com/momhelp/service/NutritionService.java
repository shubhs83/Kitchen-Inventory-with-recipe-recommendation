package com.momhelp.service;

import com.momhelp.dto.NutritionRequestDTO;
import com.momhelp.dto.NutritionResponseDTO;

public interface NutritionService {

	NutritionResponseDTO getNutritionInfo(NutritionRequestDTO requestDTO);

	NutritionResponseDTO getNutritionByVegetableName(String vegetableName);
}