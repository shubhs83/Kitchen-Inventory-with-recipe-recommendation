package com.momhelp.service.impl;

import com.momhelp.dto.ShoppingListRequestDTO;
import com.momhelp.dto.ShoppingListResponseDTO;
import com.momhelp.entity.ShoppingList;
import com.momhelp.repository.ShoppingListRepository;
import com.momhelp.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired
	private ShoppingListRepository shoppingListRepository;

	@Override
	public ShoppingListResponseDTO addItem(ShoppingListRequestDTO requestDTO) {
		ShoppingList item = new ShoppingList();
		item.setUserId(requestDTO.getUserId());
		item.setItemName(requestDTO.getItemName());
		item.setQuantity(requestDTO.getQuantity());
		item.setUnit(requestDTO.getUnit());
		item.setCategory(requestDTO.getCategory());
		item.setPriority(requestDTO.getPriority() != null ? requestDTO.getPriority() : "MEDIUM");
		item.setEstimatedPrice(requestDTO.getEstimatedPrice());
		item.setNotes(requestDTO.getNotes());
		item.setIsPurchased(false);

		ShoppingList saved = shoppingListRepository.save(item);
		return convertToDTO(saved);
	}

	@Override
	public ShoppingListResponseDTO updateItem(Long id, ShoppingListRequestDTO requestDTO) {
		ShoppingList item = shoppingListRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shopping list item not found: " + id));

		item.setItemName(requestDTO.getItemName());
		item.setQuantity(requestDTO.getQuantity());
		item.setUnit(requestDTO.getUnit());
		item.setCategory(requestDTO.getCategory());
		item.setPriority(requestDTO.getPriority());
		item.setEstimatedPrice(requestDTO.getEstimatedPrice());
		item.setNotes(requestDTO.getNotes());

		ShoppingList updated = shoppingListRepository.save(item);
		return convertToDTO(updated);
	}

	@Override
	public ShoppingListResponseDTO markAsPurchased(Long id) {
		ShoppingList item = shoppingListRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shopping list item not found: " + id));

		item.setIsPurchased(true);
		item.setPurchasedDate(new Date());

		ShoppingList updated = shoppingListRepository.save(item);
		return convertToDTO(updated);
	}

	@Override
	public ShoppingListResponseDTO markAsUnpurchased(Long id) {
		ShoppingList item = shoppingListRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shopping list item not found: " + id));

		item.setIsPurchased(false);
		item.setPurchasedDate(null);

		ShoppingList updated = shoppingListRepository.save(item);
		return convertToDTO(updated);
	}

	@Override
	public List<ShoppingListResponseDTO> getAllItems(Long userId) {
		List<ShoppingList> items = shoppingListRepository.findByUserId(userId);
		return items.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShoppingListResponseDTO> getPendingItems(Long userId) {
		List<ShoppingList> items = shoppingListRepository.findByUserIdAndIsPurchased(userId, false);
		return items.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShoppingListResponseDTO> getPurchasedItems(Long userId) {
		List<ShoppingList> items = shoppingListRepository.findByUserIdAndIsPurchased(userId, true);
		return items.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShoppingListResponseDTO> getItemsByCategory(Long userId, String category) {
		List<ShoppingList> items = shoppingListRepository.findByUserIdAndCategory(userId, category);
		return items.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShoppingListResponseDTO> getItemsByPriority(Long userId, String priority) {
		List<ShoppingList> items = shoppingListRepository.findByUserIdAndPriority(userId, priority);
		return items.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteItem(Long id) {
		shoppingListRepository.deleteById(id);
	}

	@Override
	public void clearPurchasedItems(Long userId) {
		shoppingListRepository.deleteByUserIdAndIsPurchased(userId, true);
	}

	@Override
	public void clearAllItems(Long userId) {
		List<ShoppingList> items = shoppingListRepository.findByUserId(userId);
		shoppingListRepository.deleteAll(items);
	}

	private ShoppingListResponseDTO convertToDTO(ShoppingList entity) {
		ShoppingListResponseDTO dto = new ShoppingListResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setItemName(entity.getItemName());
		dto.setQuantity(entity.getQuantity());
		dto.setUnit(entity.getUnit());
		dto.setCategory(entity.getCategory());
		dto.setPriority(entity.getPriority());
		dto.setIsPurchased(entity.getIsPurchased());
		dto.setEstimatedPrice(entity.getEstimatedPrice());
		dto.setNotes(entity.getNotes());
		dto.setAddedDate(entity.getAddedDate());
		dto.setPurchasedDate(entity.getPurchasedDate());
		return dto;
	}
}