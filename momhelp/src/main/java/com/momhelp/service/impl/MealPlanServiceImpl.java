package com.momhelp.service.impl;

import com.momhelp.dto.MealPlanRequestDTO;
import com.momhelp.dto.MealPlanResponseDTO;
import com.momhelp.entity.MealPlan;
import com.momhelp.repository.MealPlanRepository;
import com.momhelp.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MealPlanServiceImpl implements MealPlanService {

	@Autowired
	private MealPlanRepository mealPlanRepository;

	@Override
	public MealPlanResponseDTO addMealPlan(MealPlanRequestDTO requestDTO) {
		MealPlan mealPlan = new MealPlan();
		mealPlan.setUserId(requestDTO.getUserId());
		mealPlan.setMealDate(requestDTO.getMealDate());
		mealPlan.setMealType(requestDTO.getMealType());
		mealPlan.setDishName(requestDTO.getDishName());
		mealPlan.setRecipeId(requestDTO.getRecipeId());
		mealPlan.setIngredients(requestDTO.getIngredients());
		mealPlan.setPreparationTime(requestDTO.getPreparationTime());
		mealPlan.setServings(requestDTO.getServings());
		mealPlan.setNotes(requestDTO.getNotes());
		mealPlan.setIsPrepared(false);

		MealPlan saved = mealPlanRepository.save(mealPlan);
		return convertToDTO(saved);
	}

	@Override
	public MealPlanResponseDTO updateMealPlan(Long id, MealPlanRequestDTO requestDTO) {
		MealPlan mealPlan = mealPlanRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Meal plan not found: " + id));

		mealPlan.setMealDate(requestDTO.getMealDate());
		mealPlan.setMealType(requestDTO.getMealType());
		mealPlan.setDishName(requestDTO.getDishName());
		mealPlan.setRecipeId(requestDTO.getRecipeId());
		mealPlan.setIngredients(requestDTO.getIngredients());
		mealPlan.setPreparationTime(requestDTO.getPreparationTime());
		mealPlan.setServings(requestDTO.getServings());
		mealPlan.setNotes(requestDTO.getNotes());

		MealPlan updated = mealPlanRepository.save(mealPlan);
		return convertToDTO(updated);
	}

	@Override
	public MealPlanResponseDTO markAsPrepared(Long id) {
		MealPlan mealPlan = mealPlanRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Meal plan not found: " + id));

		mealPlan.setIsPrepared(true);

		MealPlan updated = mealPlanRepository.save(mealPlan);
		return convertToDTO(updated);
	}

	@Override
	public MealPlanResponseDTO markAsUnprepared(Long id) {
		MealPlan mealPlan = mealPlanRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Meal plan not found: " + id));

		mealPlan.setIsPrepared(false);

		MealPlan updated = mealPlanRepository.save(mealPlan);
		return convertToDTO(updated);
	}

	@Override
	public List<MealPlanResponseDTO> getAllMealPlans(Long userId) {
		List<MealPlan> mealPlans = mealPlanRepository.findByUserId(userId);
		return mealPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<MealPlanResponseDTO> getMealPlansByDate(Long userId, Date date) {
		List<MealPlan> mealPlans = mealPlanRepository.findByUserIdAndMealDate(userId, date);
		return mealPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<MealPlanResponseDTO> getMealPlansByDateRange(Long userId, Date startDate, Date endDate) {
		List<MealPlan> mealPlans = mealPlanRepository.findByUserIdAndMealDateBetween(userId, startDate, endDate);
		return mealPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<MealPlanResponseDTO> getMealPlansByType(Long userId, String mealType) {
		List<MealPlan> mealPlans = mealPlanRepository.findByUserIdAndMealType(userId, mealType);
		return mealPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<MealPlanResponseDTO> getPendingMealPlans(Long userId) {
		List<MealPlan> mealPlans = mealPlanRepository.findByUserIdAndIsPrepared(userId, false);
		return mealPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteMealPlan(Long id) {
		mealPlanRepository.deleteById(id);
	}

	private MealPlanResponseDTO convertToDTO(MealPlan entity) {
		MealPlanResponseDTO dto = new MealPlanResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setMealDate(entity.getMealDate());
		dto.setMealType(entity.getMealType());
		dto.setDishName(entity.getDishName());
		dto.setRecipeId(entity.getRecipeId());
		dto.setIngredients(entity.getIngredients());
		dto.setPreparationTime(entity.getPreparationTime());
		dto.setServings(entity.getServings());
		dto.setNotes(entity.getNotes());
		dto.setIsPrepared(entity.getIsPrepared());
		dto.setCreatedDate(entity.getCreatedDate());
		return dto;
	}
}