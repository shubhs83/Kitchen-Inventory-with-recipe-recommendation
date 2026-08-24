package com.momhelp.service.impl;

import com.momhelp.config.CloudVisionConfig;
import com.momhelp.service.ImageDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageDetectionServiceImpl implements ImageDetectionService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Map<String, Object>> detectVegetables(MultipartFile image) throws Exception {
		if (!validateImage(image)) {
			throw new IllegalArgumentException("Invalid image file");
		}

		String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
		List<Map<String, Object>> detectedItems = callClarifaiAPI(base64Image);

		return filterAndMapVegetables(detectedItems);
	}

	private List<Map<String, Object>> callClarifaiAPI(String base64Image) throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Key " + CloudVisionConfig.CLARIFAI_API_KEY);

			Map<String, Object> requestBody = new HashMap<>();

			Map<String, String> userAppId = new HashMap<>();
			userAppId.put("user_id", "clarifai");
			userAppId.put("app_id", "main");
			requestBody.put("user_app_id", userAppId);

			List<Map<String, Object>> inputs = new ArrayList<>();
			Map<String, Object> input = new HashMap<>();
			Map<String, Object> data = new HashMap<>();
			Map<String, String> imageData = new HashMap<>();
			imageData.put("base64", base64Image);
			data.put("image", imageData);
			input.put("data", data);
			inputs.add(input);
			requestBody.put("inputs", inputs);

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<Map> response = restTemplate.postForEntity(CloudVisionConfig.CLARIFAI_API_URL, entity,
					Map.class);

			return parseResponse(response.getBody());

		} catch (Exception e) {
			System.err.println("Error calling Clarifai API: " + e.getMessage());
			e.printStackTrace();
			throw new Exception("Failed to detect vegetables: " + e.getMessage());
		}
	}

	private List<Map<String, Object>> parseResponse(Map<String, Object> responseBody) {
		List<Map<String, Object>> detectedItems = new ArrayList<>();

		try {
			if (responseBody != null && responseBody.containsKey("outputs")) {
				List<Map<String, Object>> outputs = (List<Map<String, Object>>) responseBody.get("outputs");

				if (!outputs.isEmpty()) {
					Map<String, Object> output = outputs.get(0);
					Map<String, Object> data = (Map<String, Object>) output.get("data");

					if (data != null && data.containsKey("concepts")) {
						List<Map<String, Object>> concepts = (List<Map<String, Object>>) data.get("concepts");

						for (Map<String, Object> concept : concepts) {
							String name = (String) concept.get("name");
							Double value = ((Number) concept.get("value")).doubleValue();

							// LOWERED threshold to 50%
							if (value > CloudVisionConfig.CONFIDENCE_THRESHOLD) {
								Map<String, Object> item = new HashMap<>();
								item.put("name", name);
								item.put("confidence", value * 100);
								detectedItems.add(item);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error parsing response: " + e.getMessage());
		}

		return detectedItems;
	}

	private List<Map<String, Object>> filterAndMapVegetables(List<Map<String, Object>> detectedItems) {
		Map<String, Map<String, Object>> uniqueVegetables = new HashMap<>();

		for (Map<String, Object> item : detectedItems) {
			String detectedName = ((String) item.get("name")).toLowerCase();
			Double confidence = (Double) item.get("confidence");

			// Try to map to a known vegetable
			String mappedVegetable = mapToKnownVegetable(detectedName);

			if (mappedVegetable != null) {
				// If this vegetable already detected, keep higher confidence
				if (!uniqueVegetables.containsKey(mappedVegetable)
						|| confidence > (Double) uniqueVegetables.get(mappedVegetable).get("confidence")) {

					Map<String, Object> vegetable = new HashMap<>();
					vegetable.put("name", capitalizeFirstLetter(mappedVegetable));
					vegetable.put("confidence", confidence);
					vegetable.put("originalDetection", detectedName);
					uniqueVegetables.put(mappedVegetable, vegetable);
				}
			}
		}

		// Convert to list and sort by confidence
		return uniqueVegetables.values().stream()
				.sorted((a, b) -> Double.compare((Double) b.get("confidence"), (Double) a.get("confidence")))
				.collect(Collectors.toList());
	}

	private String mapToKnownVegetable(String detectedName) {
		detectedName = detectedName.toLowerCase().trim();

		// Direct match
		for (Map.Entry<String, String[]> entry : CloudVisionConfig.VEGETABLE_SYNONYMS.entrySet()) {
			for (String synonym : entry.getValue()) {
				if (detectedName.equals(synonym) || detectedName.contains(synonym) || synonym.contains(detectedName)) {
					return entry.getKey();
				}
			}
		}

		// Partial match for compound words
		for (Map.Entry<String, String[]> entry : CloudVisionConfig.VEGETABLE_SYNONYMS.entrySet()) {
			for (String synonym : entry.getValue()) {
				String[] detectedWords = detectedName.split("\\s+");
				String[] synonymWords = synonym.split("\\s+");

				for (String dWord : detectedWords) {
					for (String sWord : synonymWords) {
						if (dWord.length() > 3 && sWord.length() > 3
								&& (dWord.contains(sWord) || sWord.contains(dWord))) {
							return entry.getKey();
						}
					}
				}
			}
		}

		return null;
	}

	private String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	@Override
	public boolean validateImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			return false;
		}

		if (image.getSize() > CloudVisionConfig.MAX_FILE_SIZE) {
			return false;
		}

		String originalFilename = image.getOriginalFilename();
		if (originalFilename == null) {
			return false;
		}

		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
		return Arrays.asList(CloudVisionConfig.SUPPORTED_FORMATS).contains(extension);
	}

	@Override
	public String[] getSupportedFormats() {
		return CloudVisionConfig.SUPPORTED_FORMATS;
	}
}