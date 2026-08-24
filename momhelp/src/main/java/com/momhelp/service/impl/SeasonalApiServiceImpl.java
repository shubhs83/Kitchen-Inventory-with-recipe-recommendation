package com.momhelp.service.impl;

import com.momhelp.service.SeasonalApiService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SeasonalApiServiceImpl implements SeasonalApiService {

    @Value("${spoonacular.api.key}")
    private String spoonacularApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Map<String, Object>> getSeasonalRecipesFromApi(
            String season, int limit) {

        // Map Indian seasons to suitable ingredients
        String query;

        switch (season.toUpperCase()) {

            case "SUMMER":
                query = "mango";
                break;

            case "RAINY":
                query = "corn";
                break;

            case "WINTER":
            default:
                query = "carrot";
                break;
        }

        String url =
                "https://api.spoonacular.com/recipes/complexSearch"
                + "?query=" + query
                + "&number=" + limit
                + "&addRecipeInformation=true"
                + "&apiKey=" + spoonacularApiKey;

        System.out.println("========================================");
        System.out.println("SPOONACULAR SEASONAL API");
        System.out.println("Season: " + season);
        System.out.println("Query: " + query);
        System.out.println("Limit: " + limit);
        System.out.println("========================================");

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        if (response == null) {
            System.out.println("Spoonacular response is NULL");
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results =
                (List<Map<String, Object>>) response.get("results");

        if (results == null) {
            System.out.println("Spoonacular returned no results");
            return new ArrayList<>();
        }

        System.out.println(
                "Spoonacular recipes received: " + results.size());

        return results;
    }
}