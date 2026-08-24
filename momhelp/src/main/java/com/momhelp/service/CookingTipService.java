package com.momhelp.service;

import com.momhelp.dto.CookingTipRequestDTO;
import com.momhelp.dto.CookingTipResponseDTO;

import java.util.List;

public interface CookingTipService {

	CookingTipResponseDTO addTip(CookingTipRequestDTO requestDTO);

	CookingTipResponseDTO updateTip(Long id, CookingTipRequestDTO requestDTO);

	CookingTipResponseDTO toggleFavorite(Long id);

	CookingTipResponseDTO incrementViewCount(Long id);

	CookingTipResponseDTO incrementHelpfulCount(Long id);

	List<CookingTipResponseDTO> getAllTips(Long userId);

	List<CookingTipResponseDTO> getFavoriteTips(Long userId);

	List<CookingTipResponseDTO> getTipsByCategory(Long userId, String category);

	List<CookingTipResponseDTO> getTipsByDifficulty(Long userId, String difficultyLevel);

	List<CookingTipResponseDTO> searchTips(Long userId, String query);

	List<CookingTipResponseDTO> getMostViewedTips(Long userId);

	List<CookingTipResponseDTO> getMostHelpfulTips(Long userId);

	CookingTipResponseDTO getTipById(Long id);

	void deleteTip(Long id);
}