package com.momhelp.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface ImageDetectionService {

	// Detect vegetables from uploaded image
	List<Map<String, Object>> detectVegetables(MultipartFile image) throws Exception;

	// Validate image file
	boolean validateImage(MultipartFile image);

	// Get supported formats
	String[] getSupportedFormats();
}