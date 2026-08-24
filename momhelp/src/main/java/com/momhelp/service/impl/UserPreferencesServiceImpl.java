package com.momhelp.service.impl;

import com.momhelp.dto.UserPreferencesRequestDTO;
import com.momhelp.dto.UserPreferencesResponseDTO;
import com.momhelp.entity.UserPreferences;
import com.momhelp.repository.UserPreferencesRepository;
import com.momhelp.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserPreferencesServiceImpl implements UserPreferencesService {

	@Autowired
	private UserPreferencesRepository userPreferencesRepository;

	@Override
	public UserPreferencesResponseDTO savePreferences(UserPreferencesRequestDTO requestDTO) {
		if (userPreferencesRepository.existsByUserId(requestDTO.getUserId())) {
			throw new RuntimeException("Preferences already exist for this user. Use update instead.");
		}

		UserPreferences preferences = convertToEntity(requestDTO);
		UserPreferences saved = userPreferencesRepository.save(preferences);
		return convertToDTO(saved);
	}

	@Override
	public UserPreferencesResponseDTO updatePreferences(Long userId, UserPreferencesRequestDTO requestDTO) {
		UserPreferences existing = userPreferencesRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));

		updateEntity(existing, requestDTO);
		existing.setUpdatedDate(new Date());

		UserPreferences updated = userPreferencesRepository.save(existing);
		return convertToDTO(updated);
	}

	@Override
	public UserPreferencesResponseDTO getPreferencesByUserId(Long userId) {
		UserPreferences preferences = userPreferencesRepository.findByUserId(userId).orElse(null);

		if (preferences == null) {
			return getDefaultPreferences();
		}

		return convertToDTO(preferences);
	}

	@Override
	public void deletePreferences(Long userId) {
		UserPreferences preferences = userPreferencesRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));

		userPreferencesRepository.delete(preferences);
	}

	@Override
	public UserPreferencesResponseDTO getDefaultPreferences() {
		UserPreferencesResponseDTO defaultPrefs = new UserPreferencesResponseDTO();
		defaultPrefs.setUserId(1L);
		defaultPrefs.setDietaryPreference("VEGETARIAN");
		defaultPrefs.setSpiceLevel("MEDIUM");
		defaultPrefs.setAllergies(Arrays.asList());
		defaultPrefs.setFavoriteCuisines(Arrays.asList("Indian"));
		defaultPrefs.setCookingSkillLevel("INTERMEDIATE");
		defaultPrefs.setPreferredMealTypes(Arrays.asList("BREAKFAST", "LUNCH", "DINNER"));
		defaultPrefs.setAvoidIngredients(Arrays.asList());
		defaultPrefs.setMaxCookingTime(60);
		defaultPrefs.setServingSizePreference(4);
		defaultPrefs.setLanguagePreference("EN");
		return defaultPrefs;
	}

	private UserPreferences convertToEntity(UserPreferencesRequestDTO dto) {
		UserPreferences entity = new UserPreferences();
		entity.setUserId(dto.getUserId());
		updateEntity(entity, dto);
		return entity;
	}

	private void updateEntity(UserPreferences entity, UserPreferencesRequestDTO dto) {
		entity.setDietaryPreference(dto.getDietaryPreference());
		entity.setSpiceLevel(dto.getSpiceLevel());
		entity.setAllergies(listToString(dto.getAllergies()));
		entity.setFavoriteCuisines(listToString(dto.getFavoriteCuisines()));
		entity.setCookingSkillLevel(dto.getCookingSkillLevel());
		entity.setPreferredMealTypes(listToString(dto.getPreferredMealTypes()));
		entity.setAvoidIngredients(listToString(dto.getAvoidIngredients()));
		entity.setMaxCookingTime(dto.getMaxCookingTime());
		entity.setServingSizePreference(dto.getServingSizePreference());
		entity.setLanguagePreference(dto.getLanguagePreference());
	}

	private UserPreferencesResponseDTO convertToDTO(UserPreferences entity) {
		UserPreferencesResponseDTO dto = new UserPreferencesResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setDietaryPreference(entity.getDietaryPreference());
		dto.setSpiceLevel(entity.getSpiceLevel());
		dto.setAllergies(stringToList(entity.getAllergies()));
		dto.setFavoriteCuisines(stringToList(entity.getFavoriteCuisines()));
		dto.setCookingSkillLevel(entity.getCookingSkillLevel());
		dto.setPreferredMealTypes(stringToList(entity.getPreferredMealTypes()));
		dto.setAvoidIngredients(stringToList(entity.getAvoidIngredients()));
		dto.setMaxCookingTime(entity.getMaxCookingTime());
		dto.setServingSizePreference(entity.getServingSizePreference());
		dto.setLanguagePreference(entity.getLanguagePreference());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setUpdatedDate(entity.getUpdatedDate());
		return dto;
	}

	private String listToString(List<String> list) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		return String.join(",", list);
	}

	private List<String> stringToList(String str) {
		if (str == null || str.trim().isEmpty()) {
			return Arrays.asList();
		}
		return Arrays.asList(str.split(","));
	}
}