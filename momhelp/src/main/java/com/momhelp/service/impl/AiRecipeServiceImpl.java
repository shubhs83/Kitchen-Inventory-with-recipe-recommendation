package com.momhelp.service.impl;

import com.momhelp.config.GroqConfig;
import com.momhelp.dto.AiRecipeRequestDTO;
import com.momhelp.dto.AiRecipeResponseDTO;
import com.momhelp.entity.AiGeneratedRecipe;
import com.momhelp.repository.AiRecipeRepository;
import com.momhelp.service.AiRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AiRecipeServiceImpl implements AiRecipeService {

	@Autowired
	private AiRecipeRepository aiRecipeRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AiRecipeResponseDTO generateRecipe(AiRecipeRequestDTO requestDTO) {
		try {
			// Build the prompt for Groq API
			String prompt = buildPrompt(requestDTO);

			// Call Groq API
			String aiResponse = callGroqApi(prompt);

			// Parse the AI response
			AiGeneratedRecipe recipe = parseAiResponse(aiResponse, requestDTO);

			// Save to database
			AiGeneratedRecipe savedRecipe = aiRecipeRepository.save(recipe);

			return convertToDTO(savedRecipe);

		} catch (Exception e) {
			throw new RuntimeException("Failed to generate recipe: " + e.getMessage());
		}
	}

	private String buildPrompt(AiRecipeRequestDTO requestDTO) {
		String vegetables = String.join(", ", requestDTO.getVegetables());
		String mealTypeText = formatMealType(requestDTO.getMealType());
		String languageText = getLanguageName(requestDTO.getLanguage());

		StringBuilder prompt = new StringBuilder();
		prompt.append("You are an expert Indian chef. Generate a detailed recipe in ").append(languageText)
				.append(" using the following vegetables: ").append(vegetables).append(".\n\n");
		prompt.append("Requirements:\n");
		prompt.append("- Meal Type: ").append(mealTypeText).append("\n");
		prompt.append("- Servings: ").append(requestDTO.getServings()).append(" people\n");
		prompt.append("- Must use ALL the provided vegetables\n");
		prompt.append("- Include common Indian spices and ingredients\n\n");
		prompt.append("Provide the response in this EXACT format:\n\n");
		prompt.append("RECIPE NAME: [Name of the dish]\n\n");
		prompt.append("PREP TIME: [time in minutes]\n\n");
		prompt.append("COOK TIME: [time in minutes]\n\n");
		prompt.append("INGREDIENTS:\n");
		prompt.append("- [ingredient 1 with quantity]\n");
		prompt.append("- [ingredient 2 with quantity]\n");
		prompt.append("...\n\n");
		prompt.append("INSTRUCTIONS:\n");
		prompt.append("1. [Step 1]\n");
		prompt.append("2. [Step 2]\n");
		prompt.append("...\n\n");
		prompt.append("NUTRITIONAL INFO (per serving):\n");
		prompt.append("- Calories: [amount]\n");
		prompt.append("- Protein: [amount]\n");
		prompt.append("- Carbs: [amount]\n");
		prompt.append("- Fat: [amount]\n");

		return prompt.toString();
	}

	private String callGroqApi(String prompt) {
		try {
			// Prepare headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(GroqConfig.GROQ_API_KEY);

			// Prepare request body
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("model", GroqConfig.MODEL_NAME);

			List<Map<String, String>> messages = new ArrayList<>();
			Map<String, String> message = new HashMap<>();
			message.put("role", "user");
			message.put("content", prompt);
			messages.add(message);

			requestBody.put("messages", messages);
			requestBody.put("temperature", 0.7);
			requestBody.put("max_tokens", 2000);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

			// Make API call
			ResponseEntity<Map> response = restTemplate.postForEntity(GroqConfig.GROQ_API_URL, entity, Map.class);

			// Extract response
			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null && responseBody.containsKey("choices")) {
				List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
				if (!choices.isEmpty()) {
					Map<String, Object> firstChoice = choices.get(0);
					Map<String, Object> messageObj = (Map<String, Object>) firstChoice.get("message");
					return (String) messageObj.get("content");
				}
			}

			throw new RuntimeException("Invalid response from Groq API");

		} catch (Exception e) {
			throw new RuntimeException("Error calling Groq API: " + e.getMessage());
		}
	}

	private AiGeneratedRecipe parseAiResponse(String aiResponse, AiRecipeRequestDTO requestDTO) {
		AiGeneratedRecipe recipe = new AiGeneratedRecipe();

		try {
			// Extract recipe name
			String recipeName = extractValue(aiResponse, "RECIPE NAME:", "\n");
			recipe.setRecipeName(recipeName.trim());

			// Extract prep time
			String prepTimeStr = extractValue(aiResponse, "PREP TIME:", "\n");
			recipe.setPrepTime(extractMinutes(prepTimeStr));

			// Extract cook time
			String cookTimeStr = extractValue(aiResponse, "COOK TIME:", "\n");
			recipe.setCookTime(extractMinutes(cookTimeStr));

			// Calculate total time
			recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());

			// Extract ingredients
			String ingredients = extractSection(aiResponse, "INGREDIENTS:", "INSTRUCTIONS:");
			recipe.setIngredients(ingredients.trim());

			// Extract instructions
			String instructions = extractSection(aiResponse, "INSTRUCTIONS:", "NUTRITIONAL INFO");
			recipe.setInstructions(instructions.trim());

			// Extract nutritional info
			String nutritionalInfo = extractSection(aiResponse, "NUTRITIONAL INFO", null);
			recipe.setNutritionalInfo(nutritionalInfo.trim());

			// Set other fields
			recipe.setMealType(requestDTO.getMealType());
			recipe.setServings(requestDTO.getServings());
			recipe.setLanguage(requestDTO.getLanguage());
			recipe.setVegetablesUsed(String.join(", ", requestDTO.getVegetables()));

		} catch (Exception e) {
			System.err.println("Error parsing AI response: " + e.getMessage());
			// Set default values if parsing fails
			recipe.setRecipeName("Generated Recipe");
			recipe.setIngredients(aiResponse);
			recipe.setInstructions("See ingredients section for full recipe");
			recipe.setPrepTime(15);
			recipe.setCookTime(30);
			recipe.setTotalTime(45);
		}

		return recipe;
	}

	private String extractValue(String text, String start, String end) {
		int startIndex = text.indexOf(start);
		if (startIndex == -1)
			return "";

		startIndex += start.length();
		int endIndex = text.indexOf(end, startIndex);
		if (endIndex == -1)
			endIndex = text.length();

		return text.substring(startIndex, endIndex).trim();
	}

	private String extractSection(String text, String startMarker, String endMarker) {
		int startIndex = text.indexOf(startMarker);
		if (startIndex == -1)
			return "";

		startIndex += startMarker.length();

		int endIndex;
		if (endMarker != null) {
			endIndex = text.indexOf(endMarker, startIndex);
			if (endIndex == -1)
				endIndex = text.length();
		} else {
			endIndex = text.length();
		}

		return text.substring(startIndex, endIndex).trim();
	}

	private Integer extractMinutes(String timeStr) {
		try {
			// Extract numbers from string
			String numbers = timeStr.replaceAll("[^0-9]", "");
			if (numbers.isEmpty())
				return 15;
			return Integer.parseInt(numbers);
		} catch (Exception e) {
			return 15; // default value
		}
	}

	private String formatMealType(String mealType) {
		switch (mealType) {
		case "BREAKFAST":
			return "Breakfast";
		case "LUNCH_DINNER":
			return "Lunch or Dinner";
		case "DESSERT":
			return "Dessert";
		default:
			return "Any meal";
		}
	}

	private String getLanguageName(String languageCode) {
		switch (languageCode) {
		case "EN":
			return "English";
		case "HI":
			return "Hindi";
		case "TE":
			return "Telugu";
		default:
			return "English";
		}
	}

	@Override
	public AiRecipeResponseDTO getRecipeById(Long id) {
		AiGeneratedRecipe recipe = aiRecipeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
		return convertToDTO(recipe);
	}

	@Override
	public List<AiRecipeResponseDTO> getAllRecipes() {
		return aiRecipeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<AiRecipeResponseDTO> getFavoriteRecipes() {
		return aiRecipeRepository.findByIsFavoriteTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public AiRecipeResponseDTO toggleFavorite(Long id) {
		AiGeneratedRecipe recipe = aiRecipeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

		recipe.setIsFavorite(!recipe.getIsFavorite());
		AiGeneratedRecipe updated = aiRecipeRepository.save(recipe);
		return convertToDTO(updated);
	}

	@Override
	public void deleteRecipe(Long id) {
		if (!aiRecipeRepository.existsById(id)) {
			throw new RuntimeException("Recipe not found with id: " + id);
		}
		aiRecipeRepository.deleteById(id);
	}

	@Override
	public List<AiRecipeResponseDTO> getRecentRecipes() {
		return aiRecipeRepository.findRecentRecipes().stream().limit(10).map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private AiRecipeResponseDTO convertToDTO(AiGeneratedRecipe recipe) {
		return new AiRecipeResponseDTO(recipe.getId(), recipe.getRecipeName(), recipe.getIngredients(),
				recipe.getInstructions(), recipe.getMealType(), recipe.getServings(), recipe.getPrepTime(),
				recipe.getCookTime(), recipe.getTotalTime(), recipe.getLanguage(), recipe.getNutritionalInfo(),
				recipe.getVegetablesUsed(), recipe.getCreatedDate(), recipe.getIsFavorite());
	}
}