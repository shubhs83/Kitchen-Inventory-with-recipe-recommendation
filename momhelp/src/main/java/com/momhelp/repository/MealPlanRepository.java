package com.momhelp.repository;

import com.momhelp.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

	List<MealPlan> findByUserId(Long userId);

	List<MealPlan> findByUserIdAndMealDate(Long userId, Date mealDate);

	List<MealPlan> findByUserIdAndMealDateBetween(Long userId, Date startDate, Date endDate);

	List<MealPlan> findByUserIdAndMealType(Long userId, String mealType);

	List<MealPlan> findByUserIdAndIsPrepared(Long userId, Boolean isPrepared);
}