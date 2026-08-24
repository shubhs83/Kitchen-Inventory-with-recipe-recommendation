package com.momhelp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momhelp.entity.MarathiRecipe;
import com.momhelp.repository.MarathiRecipeRepository;

@Service
public class SpoonacularService {

	private static final String API_KEY = "c9d9102d0410449aaabb78c4b8490e49";

	@Autowired
	private MarathiRecipeRepository marathiRecipeRepository;

	@Autowired
	private TranslationService1 translationService;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public List<Map<String, Object>> searchRecipesByIngredient(String vegetableName, String language) {

		if ("mr".equalsIgnoreCase(language)) {
			List<MarathiRecipe> customRecipes = marathiRecipeRepository
					.findByVegetableNameIgnoreCaseAndIsCustomRecipeTrue(vegetableName);

			if (!customRecipes.isEmpty()) {
				return convertCustomRecipesToDishList(customRecipes, language);
			}
		}

		try {
			String url = "https://api.spoonacular.com/recipes/findByIngredients" + "?ingredients=" + vegetableName
					+ "&number=10" + "&ranking=2" + "&apiKey=" + API_KEY;

			String response = restTemplate.getForObject(url, String.class);
			JsonNode recipes = objectMapper.readTree(response);

			List<Map<String, Object>> dishList = new ArrayList<>();

			for (JsonNode recipe : recipes) {
				Map<String, Object> dish = new HashMap<>();
				Long recipeId = recipe.get("id").asLong();
				String dishNameEnglish = recipe.get("title").asText();

				dish.put("id", recipeId);

				if ("mr".equalsIgnoreCase(language)) {
					String dishNameMarathi = getOrTranslateDishName(recipeId, dishNameEnglish);
					dish.put("dishName", dishNameMarathi);
					dish.put("dishNameEnglish", dishNameEnglish);
				} else {
					dish.put("dishName", dishNameEnglish);
				}

				dish.put("image", recipe.has("image") ? recipe.get("image").asText() : "");
				dish.put("usedIngredientCount", recipe.get("usedIngredientCount").asInt());
				dish.put("missedIngredientCount", recipe.get("missedIngredientCount").asInt());

				dishList.add(dish);
			}

			return dishList;

		} catch (Exception e) {
			System.err.println("Error fetching recipes: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	public Map<String, Object> getRecipeDetails(Long recipeId, String language) {

		if ("mr".equalsIgnoreCase(language)) {
			Optional<MarathiRecipe> cachedRecipe = marathiRecipeRepository.findBySpoonacularRecipeId(recipeId);
			if (cachedRecipe.isPresent()) {
				return convertMarathiRecipeToMap(cachedRecipe.get());
			}
		}

		try {
			String url = "https://api.spoonacular.com/recipes/" + recipeId + "/information?includeNutrition=false"
					+ "&apiKey=" + API_KEY;

			String response = restTemplate.getForObject(url, String.class);
			JsonNode recipe = objectMapper.readTree(response);

			Map<String, Object> recipeDetails = buildRecipeDetailsFromJson(recipe);

			if ("mr".equalsIgnoreCase(language)) {
				recipeDetails = translateAndCacheRecipe(recipeId, recipe, recipeDetails);
			}

			return recipeDetails;

		} catch (Exception e) {
			System.err.println("Error fetching recipe details: " + e.getMessage());
			return Collections.emptyMap();
		}
	}

	private Map<String, Object> buildRecipeDetailsFromJson(JsonNode recipe) {
		Map<String, Object> recipeDetails = new HashMap<>();

		recipeDetails.put("id", recipe.get("id").asLong());
		recipeDetails.put("dishName", recipe.get("title").asText());
		recipeDetails.put("image", recipe.has("image") ? recipe.get("image").asText() : "");
		recipeDetails.put("servings", recipe.has("servings") ? recipe.get("servings").asInt() : 4);
		recipeDetails.put("readyInMinutes", recipe.has("readyInMinutes") ? recipe.get("readyInMinutes").asInt() : 30);

		List<Map<String, String>> ingredients = new ArrayList<>();
		if (recipe.has("extendedIngredients")) {
			for (JsonNode ing : recipe.get("extendedIngredients")) {
				Map<String, String> ingredient = new HashMap<>();
				ingredient.put("ingredientName", ing.get("original").asText());
				ingredient.put("quantity", ing.has("amount") ? ing.get("amount").asText() : "");
				ingredient.put("unit", ing.has("unit") ? ing.get("unit").asText() : "");
				ingredients.add(ingredient);
			}
		}
		recipeDetails.put("ingredients", ingredients);

		String instructions = extractInstructions(recipe);
		recipeDetails.put("instructions", instructions);

		return recipeDetails;
	}

	private String extractInstructions(JsonNode recipe) {
		String instructions = "";

		if (recipe.has("instructions") && !recipe.get("instructions").isNull()) {
			instructions = recipe.get("instructions").asText();
		} else if (recipe.has("analyzedInstructions") && recipe.get("analyzedInstructions").size() > 0) {

			StringBuilder sb = new StringBuilder();
			JsonNode steps = recipe.get("analyzedInstructions").get(0).get("steps");

			for (JsonNode step : steps) {
				sb.append(step.get("number").asInt()).append(". ").append(step.get("step").asText()).append("\n");
			}
			instructions = sb.toString();
		}

		return instructions;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> translateAndCacheRecipe(Long recipeId, JsonNode recipe,
			Map<String, Object> recipeDetails) {
		try {
			String dishNameEnglish = (String) recipeDetails.get("dishName");
			String instructionsEnglish = (String) recipeDetails.get("instructions");
			List<Map<String, String>> ingredientsEnglish = (List<Map<String, String>>) recipeDetails.get("ingredients");

			String dishNameMarathi = translationService.translateToMarathi(dishNameEnglish);
			String instructionsMarathi = translationService.translateToMarathi(instructionsEnglish);

			List<Map<String, String>> ingredientsMarathi = new ArrayList<>();
			for (Map<String, String> ing : ingredientsEnglish) {
				Map<String, String> marathiIng = new HashMap<>();
				marathiIng.put("ingredientName", translationService.translateToMarathi(ing.get("ingredientName")));
				marathiIng.put("quantity", ing.get("quantity"));
				marathiIng.put("unit", translationService.translateToMarathi(ing.get("unit")));
				ingredientsMarathi.add(marathiIng);
			}

			MarathiRecipe marathiRecipe = new MarathiRecipe();
			marathiRecipe.setSpoonacularRecipeId(recipeId);
			marathiRecipe.setDishNameEnglish(dishNameEnglish);
			marathiRecipe.setDishNameMarathi(dishNameMarathi);
			marathiRecipe.setInstructionsEnglish(instructionsEnglish);
			marathiRecipe.setInstructionsMarathi(instructionsMarathi);
			marathiRecipe.setIsCustomRecipe(false);

			marathiRecipeRepository.save(marathiRecipe);

			recipeDetails.put("dishName", dishNameMarathi);
			recipeDetails.put("instructions", instructionsMarathi);
			recipeDetails.put("ingredients", ingredientsMarathi);

		} catch (Exception e) {
			System.err.println("Translation failed: " + e.getMessage());
		}

		return recipeDetails;
	}

	private String getOrTranslateDishName(Long recipeId, String englishName) {
		Optional<MarathiRecipe> cached = marathiRecipeRepository.findBySpoonacularRecipeId(recipeId);
		if (cached.isPresent()) {
			return cached.get().getDishNameMarathi();
		}
		return translationService.translateToMarathi(englishName);
	}

	private List<Map<String, Object>> convertCustomRecipesToDishList(List<MarathiRecipe> recipes, String language) {

		List<Map<String, Object>> dishList = new ArrayList<>();

		for (MarathiRecipe recipe : recipes) {
			Map<String, Object> dish = new HashMap<>();
			dish.put("id", recipe.getSpoonacularRecipeId() != null ? recipe.getSpoonacularRecipeId() : recipe.getId());

			if ("mr".equalsIgnoreCase(language)) {
				dish.put("dishName", recipe.getDishNameMarathi());
			} else {
				dish.put("dishName", recipe.getDishNameEnglish());
			}

			dish.put("image", "");
			dish.put("usedIngredientCount", 1);
			dish.put("missedIngredientCount", 0);
			dish.put("isCustom", true);

			dishList.add(dish);
		}

		return dishList;
	}

	private Map<String, Object> convertMarathiRecipeToMap(MarathiRecipe recipe) {
		Map<String, Object> recipeMap = new HashMap<>();

		recipeMap.put("id", recipe.getSpoonacularRecipeId() != null ? recipe.getSpoonacularRecipeId() : recipe.getId());
		recipeMap.put("dishName", recipe.getDishNameMarathi());
		recipeMap.put("instructions", recipe.getInstructionsMarathi());
		recipeMap.put("servings", 4);
		recipeMap.put("readyInMinutes", 30);
		recipeMap.put("ingredients", new ArrayList<>());

		return recipeMap;
	}
}