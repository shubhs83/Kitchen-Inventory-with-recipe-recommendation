package com.momhelp.service;

import com.momhelp.dto.UserPreferencesRequestDTO;
import com.momhelp.dto.UserPreferencesResponseDTO;

public interface UserPreferencesService {

	UserPreferencesResponseDTO savePreferences(UserPreferencesRequestDTO requestDTO);

	UserPreferencesResponseDTO updatePreferences(Long userId, UserPreferencesRequestDTO requestDTO);

	UserPreferencesResponseDTO getPreferencesByUserId(Long userId);

	void deletePreferences(Long userId);

	UserPreferencesResponseDTO getDefaultPreferences();
}