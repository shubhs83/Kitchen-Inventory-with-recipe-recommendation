package com.momhelp.repository;

import com.momhelp.entity.CookingTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookingTipRepository extends JpaRepository<CookingTip, Long> {

	List<CookingTip> findByUserId(Long userId);

	List<CookingTip> findByUserIdAndIsFavorite(Long userId, Boolean isFavorite);

	List<CookingTip> findByUserIdAndCategory(Long userId, String category);

	List<CookingTip> findByUserIdAndDifficultyLevel(Long userId, String difficultyLevel);

	List<CookingTip> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

	List<CookingTip> findByUserIdOrderByViewCountDesc(Long userId);

	List<CookingTip> findByUserIdOrderByHelpfulCountDesc(Long userId);
}