package com.momhelp.repository;

import com.momhelp.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

	List<ShoppingList> findByUserId(Long userId);

	List<ShoppingList> findByUserIdAndIsPurchased(Long userId, Boolean isPurchased);

	List<ShoppingList> findByUserIdAndCategory(Long userId, String category);

	List<ShoppingList> findByUserIdAndPriority(Long userId, String priority);

	void deleteByUserIdAndIsPurchased(Long userId, Boolean isPurchased);
}