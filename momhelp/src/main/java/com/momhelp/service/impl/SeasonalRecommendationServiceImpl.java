package com.momhelp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momhelp.dto.SeasonalDishDTO;
import com.momhelp.entity.SeasonalDish;
import com.momhelp.repository.SeasonalDishRepository;
import com.momhelp.repository.UsageHistoryRepository;
import com.momhelp.service.SeasonalApiService;
import com.momhelp.service.SeasonalRecommendationService;

@Service
public class SeasonalRecommendationServiceImpl implements SeasonalRecommendationService {

    @Autowired
    private SeasonalDishRepository seasonalDishRepository;

    @Autowired
    private UsageHistoryRepository usageHistoryRepository;

    @Autowired
    private SeasonalApiService seasonalApiService;

    @Override
    public String getCurrentSeason() {

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        /*
         * Indian seasonal classification:
         *
         * Summer : March - June
         * Rainy  : July - September
         * Winter : October - February
         */

        if (month >= 3 && month <= 6) {
            return "SUMMER";

        } else if (month >= 7 && month <= 9) {
            return "RAINY";

        } else {
            return "WINTER";
        }
    }

    @Override
    public List<SeasonalDishDTO> getSeasonalRecommendations(
            String season, int limit) {

        try {

            /*
             * STEP 1:
             * Try Spoonacular first.
             *
             * This makes the seasonal recommendations
             * dynamically fetched from the external API.
             */

            List<Map<String, Object>> apiRecipes =
                    seasonalApiService.getSeasonalRecipesFromApi(
                            season,
                            limit);

            System.out.println("----------------------------------------");
            System.out.println("Current Season: " + season);
            System.out.println(
                    "Spoonacular recipes received: "
                    + apiRecipes.size());
            System.out.println("----------------------------------------");

            /*
             * STEP 2:
             * If Spoonacular returned recipes,
             * convert them to our DTO format.
             */

            if (apiRecipes != null && !apiRecipes.isEmpty()) {

                return convertApiRecipesToDTO(
                        apiRecipes,
                        season);
            }

            /*
             * STEP 3:
             * If Spoonacular returns no recipes,
             * use database recipes as fallback.
             */

            System.out.println(
                    "No Spoonacular recipes found. "
                    + "Using database fallback.");

            return getSeasonalRecommendationsFromDb(
                    season,
                    limit);

        } catch (Exception e) {

            /*
             * STEP 4:
             * If Spoonacular is unavailable,
             * application should not crash.
             *
             * Use MySQL database as fallback.
             */

            System.err.println(
                    "Spoonacular seasonal API failed: "
                    + e.getMessage());

            e.printStackTrace();

            return getSeasonalRecommendationsFromDb(
                    season,
                    limit);
        }
    }

    @Override
    public List<SeasonalDishDTO> getCurrentSeasonRecommendations(
            int limit) {

        /*
         * Automatically determine the current season
         * from the current system date.
         */

        String currentSeason = getCurrentSeason();

        System.out.println(
                "Automatically detected season: "
                + currentSeason);

        return getSeasonalRecommendations(
                currentSeason,
                limit);
    }

    @Override
    public List<String> getRecentDishes(int days) {

        Calendar cal = Calendar.getInstance();

        cal.add(
                Calendar.DAY_OF_MONTH,
                -days);

        Date startDate = cal.getTime();

        return usageHistoryRepository
                .findRecentHistory(startDate)
                .stream()
                .map(history -> history.getDishName())
                .filter(dishName ->
                        dishName != null
                        && !dishName.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    // =========================================================
    // CONVERT SPOONACULAR RECIPES TO OUR DTO
    // =========================================================

    private List<SeasonalDishDTO> convertApiRecipesToDTO(
            List<Map<String, Object>> recipes,
            String season) {

        List<SeasonalDishDTO> result =
                new ArrayList<>();

        for (Map<String, Object> recipe : recipes) {

            try {

                Long id = null;

                if (recipe.get("id") != null) {

                    Object idObject =
                            recipe.get("id");

                    if (idObject instanceof Number) {

                        id = ((Number) idObject).longValue();

                    } else {

                        id = Long.parseLong(
                                idObject.toString());
                    }
                }

                String dishName =
                        recipe.get("title") != null
                        ? recipe.get("title").toString()
                        : "Unknown Recipe";

                String description =
                        recipe.get("summary") != null
                        ? cleanHtml(
                                recipe.get("summary").toString())
                        : "Seasonal recipe";

                String imageUrl =
                        recipe.get("image") != null
                        ? recipe.get("image").toString()
                        : null;

                Integer prepTime =
                        getIntegerValue(
                                recipe.get("readyInMinutes"));

                Integer cookTime = 0;

                String ingredients =
                        getIngredients(recipe);

                String instructions =
                        getInstructions(recipe);

                SeasonalDishDTO dto =
                        new SeasonalDishDTO(
                                id,
                                dishName,
                                description,
                                season,
                                ingredients,
                                instructions,
                                prepTime,
                                cookTime,
                                imageUrl
                        );

                result.add(dto);

            } catch (Exception e) {

                System.err.println(
                        "Error converting Spoonacular recipe: "
                        + e.getMessage());
            }
        }

        return result;
    }

    // =========================================================
    // EXTRACT INGREDIENTS
    // =========================================================

    @SuppressWarnings("unchecked")
    private String getIngredients(
            Map<String, Object> recipe) {

        Object ingredientsObject =
                recipe.get("extendedIngredients");

        if (!(ingredientsObject instanceof List)) {
            return "";
        }

        List<Map<String, Object>> ingredients =
                (List<Map<String, Object>>) ingredientsObject;

        List<String> names =
                new ArrayList<>();

        for (Map<String, Object> ingredient
                : ingredients) {

            Object original =
                    ingredient.get("original");

            if (original != null) {

                names.add(
                        original.toString());
            }
        }

        return String.join(
                ", ",
                names);
    }

    // =========================================================
    // EXTRACT INSTRUCTIONS
    // =========================================================

    private String getInstructions(
            Map<String, Object> recipe) {

        Object instructions =
                recipe.get("instructions");

        if (instructions != null) {

            return cleanHtml(
                    instructions.toString());
        }

        return "Instructions are not available.";
    }

    // =========================================================
    // INTEGER CONVERSION
    // =========================================================

    private Integer getIntegerValue(
            Object value) {

        if (value == null) {
            return 0;
        }

        if (value instanceof Number) {

            return ((Number) value).intValue();
        }

        try {

            return Integer.parseInt(
                    value.toString());

        } catch (NumberFormatException e) {

            return 0;
        }
    }

    // =========================================================
    // REMOVE HTML FROM SPOONACULAR SUMMARY
    // =========================================================

    private String cleanHtml(
            String text) {

        if (text == null) {
            return "";
        }

        return text
                .replaceAll(
                        "<[^>]*>",
                        "")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .trim();
    }

    // =========================================================
    // DATABASE FALLBACK
    // =========================================================

    private List<SeasonalDishDTO>
    getSeasonalRecommendationsFromDb(
            String season,
            int limit) {

        List<SeasonalDish> allDishes =
                seasonalDishRepository
                        .findBySeason(
                                season.toUpperCase());

        Collections.shuffle(allDishes);

        return allDishes
                .stream()
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =========================================================
    // DATABASE ENTITY → DTO
    // =========================================================

    private SeasonalDishDTO convertToDTO(
            SeasonalDish dish) {

        return new SeasonalDishDTO(
                dish.getId(),
                dish.getDishName(),
                dish.getDescription(),
                dish.getSeason(),
                dish.getIngredients(),
                dish.getInstructions(),
                dish.getPrepTime(),
                dish.getCookTime(),
                dish.getImageUrl()
        );
    }
}