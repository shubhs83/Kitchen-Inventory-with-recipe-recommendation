package com.momhelp.controller;

import com.momhelp.dto.SeasonalDishDTO;
import com.momhelp.service.SeasonalRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seasonal")
@CrossOrigin(origins = "http://localhost:3000")
public class SeasonalRecommendationController {

	@Autowired
	private SeasonalRecommendationService seasonalService;

	// Get current season
	@GetMapping("/current-season")
	public ResponseEntity<?> getCurrentSeason() {
		Map<String, Object> response = new HashMap<>();
		response.put("season", seasonalService.getCurrentSeason());
		return ResponseEntity.ok(response);
    }

	// Get seasonal recommendations for current season
	@GetMapping("/recommendations")
	public ResponseEntity<?> getCurrentSeasonRecommendations(@RequestParam(defaultValue = "6") int limit) {
		try {
			String currentSeason = seasonalService.getCurrentSeason();
			List<SeasonalDishDTO> dishes = seasonalService.getCurrentSeasonRecommendations(limit);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("season", currentSeason);
			response.put("dishes", dishes);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("success", false);
			error.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// Get recommendations for specific season
	@GetMapping("/recommendations/{season}")
	public ResponseEntity<?> getSeasonRecommendations(@PathVariable String season,
			@RequestParam(defaultValue = "6") int limit) {
		try {
			List<SeasonalDishDTO> dishes = seasonalService.getSeasonalRecommendations(season, limit);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("season", season);
			response.put("dishes", dishes);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("success", false);
			error.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}
}