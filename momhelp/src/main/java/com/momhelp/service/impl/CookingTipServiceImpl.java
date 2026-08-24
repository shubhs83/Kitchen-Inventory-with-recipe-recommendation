package com.momhelp.service.impl;

import com.momhelp.dto.CookingTipRequestDTO;
import com.momhelp.dto.CookingTipResponseDTO;
import com.momhelp.entity.CookingTip;
import com.momhelp.repository.CookingTipRepository;
import com.momhelp.service.CookingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CookingTipServiceImpl implements CookingTipService {

	@Autowired
	private CookingTipRepository cookingTipRepository;

	@Override
	public CookingTipResponseDTO addTip(CookingTipRequestDTO requestDTO) {
		CookingTip tip = new CookingTip();
		tip.setUserId(requestDTO.getUserId());
		tip.setTitle(requestDTO.getTitle());
		tip.setCategory(requestDTO.getCategory());
		tip.setTipContent(requestDTO.getTipContent());
		tip.setDifficultyLevel(requestDTO.getDifficultyLevel());
		tip.setTags(listToString(requestDTO.getTags()));
		tip.setVideoUrl(requestDTO.getVideoUrl());
		tip.setImageUrl(requestDTO.getImageUrl());
		tip.setIsFavorite(false);
		tip.setViewCount(0);
		tip.setHelpfulCount(0);

		CookingTip saved = cookingTipRepository.save(tip);
		return convertToDTO(saved);
	}

	@Override
	public CookingTipResponseDTO updateTip(Long id, CookingTipRequestDTO requestDTO) {
		CookingTip tip = cookingTipRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cooking tip not found: " + id));

		tip.setTitle(requestDTO.getTitle());
		tip.setCategory(requestDTO.getCategory());
		tip.setTipContent(requestDTO.getTipContent());
		tip.setDifficultyLevel(requestDTO.getDifficultyLevel());
		tip.setTags(listToString(requestDTO.getTags()));
		tip.setVideoUrl(requestDTO.getVideoUrl());
		tip.setImageUrl(requestDTO.getImageUrl());
		tip.setUpdatedDate(new Date());

		CookingTip updated = cookingTipRepository.save(tip);
		return convertToDTO(updated);
	}

	@Override
	public CookingTipResponseDTO toggleFavorite(Long id) {
		CookingTip tip = cookingTipRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cooking tip not found: " + id));

		tip.setIsFavorite(!tip.getIsFavorite());
		tip.setUpdatedDate(new Date());

		CookingTip updated = cookingTipRepository.save(tip);
		return convertToDTO(updated);
	}

	@Override
	public CookingTipResponseDTO incrementViewCount(Long id) {
		CookingTip tip = cookingTipRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cooking tip not found: " + id));

		tip.setViewCount(tip.getViewCount() + 1);

		CookingTip updated = cookingTipRepository.save(tip);
		return convertToDTO(updated);
	}

	@Override
	public CookingTipResponseDTO incrementHelpfulCount(Long id) {
		CookingTip tip = cookingTipRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cooking tip not found: " + id));

		tip.setHelpfulCount(tip.getHelpfulCount() + 1);

		CookingTip updated = cookingTipRepository.save(tip);
		return convertToDTO(updated);
	}

	@Override
	public List<CookingTipResponseDTO> getAllTips(Long userId) {
		List<CookingTip> tips = cookingTipRepository.findByUserId(userId);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> getFavoriteTips(Long userId) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdAndIsFavorite(userId, true);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> getTipsByCategory(Long userId, String category) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdAndCategory(userId, category);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> getTipsByDifficulty(Long userId, String difficultyLevel) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdAndDifficultyLevel(userId, difficultyLevel);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> searchTips(Long userId, String query) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdAndTitleContainingIgnoreCase(userId, query);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> getMostViewedTips(Long userId) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdOrderByViewCountDesc(userId);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<CookingTipResponseDTO> getMostHelpfulTips(Long userId) {
		List<CookingTip> tips = cookingTipRepository.findByUserIdOrderByHelpfulCountDesc(userId);
		return tips.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public CookingTipResponseDTO getTipById(Long id) {
		CookingTip tip = cookingTipRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cooking tip not found: " + id));
		return convertToDTO(tip);
	}

	@Override
	public void deleteTip(Long id) {
		cookingTipRepository.deleteById(id);
	}

	private CookingTipResponseDTO convertToDTO(CookingTip entity) {
		CookingTipResponseDTO dto = new CookingTipResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setTitle(entity.getTitle());
		dto.setCategory(entity.getCategory());
		dto.setTipContent(entity.getTipContent());
		dto.setDifficultyLevel(entity.getDifficultyLevel());
		dto.setTags(stringToList(entity.getTags()));
		dto.setVideoUrl(entity.getVideoUrl());
		dto.setImageUrl(entity.getImageUrl());
		dto.setIsFavorite(entity.getIsFavorite());
		dto.setViewCount(entity.getViewCount());
		dto.setHelpfulCount(entity.getHelpfulCount());
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