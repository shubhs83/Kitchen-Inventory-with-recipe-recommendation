package com.momhelp.controller;

import com.momhelp.dto.ShoppingListRequestDTO;
import com.momhelp.dto.ShoppingListResponseDTO;
import com.momhelp.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shopping")
@CrossOrigin(origins = "http://localhost:3000")
public class ShoppingListController {

	@Autowired
	private ShoppingListService shoppingListService;

	@PostMapping("/add")
	public ResponseEntity<?> addItem(@RequestBody ShoppingListRequestDTO requestDTO) {
		try {
			ShoppingListResponseDTO item = shoppingListService.addItem(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Item added to shopping list!");
			response.put("data", item);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ShoppingListRequestDTO requestDTO) {
		try {
			ShoppingListResponseDTO item = shoppingListService.updateItem(id, requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Item updated successfully!");
			response.put("data", item);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/purchase")
	public ResponseEntity<?> markAsPurchased(@PathVariable Long id) {
		try {
			ShoppingListResponseDTO item = shoppingListService.markAsPurchased(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Item marked as purchased!");
			response.put("data", item);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/unpurchase")
	public ResponseEntity<?> markAsUnpurchased(@PathVariable Long id) {
		try {
			ShoppingListResponseDTO item = shoppingListService.markAsUnpurchased(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Item marked as pending!");
			response.put("data", item);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllItems(@PathVariable Long userId) {
		try {
			List<ShoppingListResponseDTO> items = shoppingListService.getAllItems(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", items);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/pending")
	public ResponseEntity<?> getPendingItems(@PathVariable Long userId) {
		try {
			List<ShoppingListResponseDTO> items = shoppingListService.getPendingItems(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", items);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/purchased")
	public ResponseEntity<?> getPurchasedItems(@PathVariable Long userId) {
		try {
			List<ShoppingListResponseDTO> items = shoppingListService.getPurchasedItems(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", items);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/category/{category}")
	public ResponseEntity<?> getItemsByCategory(@PathVariable Long userId, @PathVariable String category) {
		try {
			List<ShoppingListResponseDTO> items = shoppingListService.getItemsByCategory(userId, category);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", items);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/priority/{priority}")
	public ResponseEntity<?> getItemsByPriority(@PathVariable Long userId, @PathVariable String priority) {
		try {
			List<ShoppingListResponseDTO> items = shoppingListService.getItemsByPriority(userId, priority);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", items);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable Long id) {
		try {
			shoppingListService.deleteItem(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Item deleted successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/user/{userId}/purchased")
	public ResponseEntity<?> clearPurchasedItems(@PathVariable Long userId) {
		try {
			shoppingListService.clearPurchasedItems(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Purchased items cleared!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/user/{userId}/all")
	public ResponseEntity<?> clearAllItems(@PathVariable Long userId) {
		try {
			shoppingListService.clearAllItems(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "All items cleared!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}