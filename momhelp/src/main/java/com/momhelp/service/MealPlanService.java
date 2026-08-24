package com.momhelp.service;

import com.momhelp.dto.MealPlanRequestDTO;
import com.momhelp.dto.MealPlanResponseDTO;

import java.util.Date;
import java.util.List;

public interface MealPlanService {

	MealPlanResponseDTO addMealPlan(MealPlanRequestDTO requestDTO);

	MealPlanResponseDTO updateMealPlan(Long id, MealPlanRequestDTO requestDTO);

	MealPlanResponseDTO markAsPrepared(Long id);

	MealPlanResponseDTO markAsUnprepared(Long id);

	List<MealPlanResponseDTO> getAllMealPlans(Long userId);

	List<MealPlanResponseDTO> getMealPlansByDate(Long userId, Date date);

	List<MealPlanResponseDTO> getMealPlansByDateRange(Long userId, Date startDate, Date endDate);

	List<MealPlanResponseDTO> getMealPlansByType(Long userId, String mealType);

	List<MealPlanResponseDTO> getPendingMealPlans(Long userId);

	void deleteMealPlan(Long id);
}