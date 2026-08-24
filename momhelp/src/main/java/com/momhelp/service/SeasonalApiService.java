package com.momhelp.service;

import java.util.List;
import java.util.Map;

public interface SeasonalApiService {

	List<Map<String, Object>> getSeasonalRecipesFromApi(String season, int limit);

}
