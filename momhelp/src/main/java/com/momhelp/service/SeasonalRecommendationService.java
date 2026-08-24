package com.momhelp.service;

import com.momhelp.dto.SeasonalDishDTO;
import java.util.List;

public interface SeasonalRecommendationService {
	String getCurrentSeason();

	List<SeasonalDishDTO> getSeasonalRecommendations(String season, int limit);

	List<SeasonalDishDTO> getCurrentSeasonRecommendations(int limit);

	List<String> getRecentDishes(int days);
}