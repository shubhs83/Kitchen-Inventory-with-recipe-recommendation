package com.momhelp.service.impl;

import com.momhelp.config.NutritionApiConfig;
import com.momhelp.dto.NutritionRequestDTO;
import com.momhelp.dto.NutritionResponseDTO;
import com.momhelp.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NutritionServiceImpl implements NutritionService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public NutritionResponseDTO getNutritionInfo(NutritionRequestDTO requestDTO) {
		try {
			return callNutritionixAPI(requestDTO);
		} catch (Exception e) {
			System.err.println("API call failed, using fallback data: " + e.getMessage());
			return getFallbackNutrition(requestDTO.getFoodItem(), requestDTO.getQuantity());
		}
	}

	@Override
	public NutritionResponseDTO getNutritionByVegetableName(String vegetableName) {
		NutritionRequestDTO requestDTO = new NutritionRequestDTO(vegetableName, 100.0, "grams");
		return getNutritionInfo(requestDTO);
	}

	private NutritionResponseDTO callNutritionixAPI(NutritionRequestDTO requestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-app-id", NutritionApiConfig.NUTRITIONIX_APP_ID);
		headers.set("x-app-key", NutritionApiConfig.NUTRITIONIX_API_KEY);

		Map<String, Object> requestBody = new HashMap<>();
		String query = requestDTO.getQuantity() + " " + (requestDTO.getUnit() != null ? requestDTO.getUnit() : "grams")
				+ " " + requestDTO.getFoodItem();
		requestBody.put("query", query);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(NutritionApiConfig.NUTRITIONIX_API_URL, entity,
				Map.class);

		return parseNutritionixResponse(response.getBody());
	}

	private NutritionResponseDTO parseNutritionixResponse(Map<String, Object> responseBody) {
		NutritionResponseDTO nutritionDTO = new NutritionResponseDTO();

		try {
			if (responseBody != null && responseBody.containsKey("foods")) {
				List<Map<String, Object>> foods = (List<Map<String, Object>>) responseBody.get("foods");

				if (!foods.isEmpty()) {
					Map<String, Object> food = foods.get(0);

					nutritionDTO.setFoodName((String) food.get("food_name"));
					nutritionDTO.setServingSize(((Number) food.get("serving_weight_grams")).doubleValue());
					nutritionDTO.setServingUnit("grams");
					nutritionDTO.setCalories(((Number) food.get("nf_calories")).doubleValue());
					nutritionDTO.setProtein(((Number) food.get("nf_protein")).doubleValue());
					nutritionDTO.setCarbohydrates(((Number) food.get("nf_total_carbohydrate")).doubleValue());
					nutritionDTO.setFat(((Number) food.get("nf_total_fat")).doubleValue());
					nutritionDTO.setFiber(((Number) food.getOrDefault("nf_dietary_fiber", 0)).doubleValue());
					nutritionDTO.setSugar(((Number) food.getOrDefault("nf_sugars", 0)).doubleValue());
					nutritionDTO.setSodium(((Number) food.getOrDefault("nf_sodium", 0)).doubleValue());
					nutritionDTO.setVitaminC(0.0);
					nutritionDTO.setCalcium(0.0);
					nutritionDTO.setIron(0.0);
				}
			}
		} catch (Exception e) {
			System.err.println("Error parsing Nutritionix response: " + e.getMessage());
		}

		return nutritionDTO;
	}

	private NutritionResponseDTO getFallbackNutrition(String vegetableName, Double quantity) {
		NutritionResponseDTO nutritionDTO = new NutritionResponseDTO();

		String key = vegetableName.toLowerCase();
		NutritionApiConfig.NutritionData data = NutritionApiConfig.VEGETABLE_NUTRITION.get(key);

		if (data == null) {
			data = NutritionApiConfig.VEGETABLE_NUTRITION.get("potato");
		}

		double multiplier = quantity / 100.0;

		nutritionDTO.setFoodName(vegetableName);
		nutritionDTO.setServingSize(quantity);
		nutritionDTO.setServingUnit("grams");
		nutritionDTO.setCalories(data.calories * multiplier);
		nutritionDTO.setProtein(data.protein * multiplier);
		nutritionDTO.setCarbohydrates(data.carbs * multiplier);
		nutritionDTO.setFat(data.fat * multiplier);
		nutritionDTO.setFiber(data.fiber * multiplier);
		nutritionDTO.setSugar(0.0);
		nutritionDTO.setSodium(0.0);
		nutritionDTO.setVitaminC(0.0);
		nutritionDTO.setCalcium(0.0);
		nutritionDTO.setIron(0.0);

		return nutritionDTO;
	}
}