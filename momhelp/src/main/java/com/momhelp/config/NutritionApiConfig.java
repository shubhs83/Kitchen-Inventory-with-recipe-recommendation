package com.momhelp.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NutritionApiConfig {

	// Nutritionix API (FREE - 200 requests/day)
	public static final String NUTRITIONIX_API_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

	// Get FREE API key from https://developer.nutritionix.com/
	public static final String NUTRITIONIX_APP_ID = "YOUR_APP_ID_HERE";
	public static final String NUTRITIONIX_API_KEY = "YOUR_API_KEY_HERE";

	// Common Indian vegetable nutrition data (fallback)
	public static final java.util.Map<String, NutritionData> VEGETABLE_NUTRITION = new java.util.HashMap<String, NutritionData>() {
		{
			put("potato", new NutritionData(77, 2.0, 17.0, 0.1, 12.0));
			put("tomato", new NutritionData(18, 0.9, 3.9, 0.2, 14.0));
			put("onion", new NutritionData(40, 1.1, 9.3, 0.1, 7.0));
			put("spinach", new NutritionData(23, 2.9, 3.6, 0.4, 99.0));
			put("carrot", new NutritionData(41, 0.9, 9.6, 0.2, 33.0));
			put("cabbage", new NutritionData(25, 1.3, 5.8, 0.1, 40.0));
			put("cauliflower", new NutritionData(25, 1.9, 5.0, 0.3, 22.0));
			put("broccoli", new NutritionData(34, 2.8, 7.0, 0.4, 47.0));
			put("pea", new NutritionData(81, 5.4, 14.5, 0.4, 25.0));
			put("corn", new NutritionData(86, 3.3, 19.0, 1.4, 7.0));
			put("cucumber", new NutritionData(15, 0.7, 3.6, 0.1, 16.0));
			put("eggplant", new NutritionData(25, 1.0, 5.9, 0.2, 9.0));
			put("bell pepper", new NutritionData(31, 1.0, 6.0, 0.3, 80.0));
			put("okra", new NutritionData(33, 1.9, 7.5, 0.2, 23.0));
			put("pumpkin", new NutritionData(26, 1.0, 6.5, 0.1, 19.0));
		}
	};

	public static class NutritionData {
		public double calories;
		public double protein;
		public double carbs;
		public double fat;
		public double fiber;

		public NutritionData(double calories, double protein, double carbs, double fat, double fiber) {
			this.calories = calories;
			this.protein = protein;
			this.carbs = carbs;
			this.fat = fat;
			this.fiber = fiber;
		}
	}
}