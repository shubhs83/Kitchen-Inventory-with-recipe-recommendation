package com.momhelp.service;

import com.momhelp.dto.ShoppingListRequestDTO;
import com.momhelp.dto.ShoppingListResponseDTO;

import java.util.List;

public interface ShoppingListService {

	ShoppingListResponseDTO addItem(ShoppingListRequestDTO requestDTO);

	ShoppingListResponseDTO updateItem(Long id, ShoppingListRequestDTO requestDTO);

	ShoppingListResponseDTO markAsPurchased(Long id);

	ShoppingListResponseDTO markAsUnpurchased(Long id);

	List<ShoppingListResponseDTO> getAllItems(Long userId);

	List<ShoppingListResponseDTO> getPendingItems(Long userId);

	List<ShoppingListResponseDTO> getPurchasedItems(Long userId);

	List<ShoppingListResponseDTO> getItemsByCategory(Long userId, String category);

	List<ShoppingListResponseDTO> getItemsByPriority(Long userId, String priority);

	void deleteItem(Long id);

	void clearPurchasedItems(Long userId);

	void clearAllItems(Long userId);
}