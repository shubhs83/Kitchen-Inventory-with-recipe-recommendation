package com.momhelp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TranslationService1 {

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public String translateToMarathi(String englishText) {
		if (englishText == null || englishText.trim().isEmpty()) {
			return "";
		}

		try {
			return translateUsingLibreTranslate(englishText);
		} catch (Exception e) {
			System.err.println("Translation failed: " + e.getMessage());
			return englishText;
		}
	}

	private String translateUsingLibreTranslate(String text) {
		try {
			String url = "https://libretranslate.com/translate";

			String requestBody = String.format(
					"{\"q\": \"%s\", \"source\": \"en\", \"target\": \"mr\", \"format\": \"text\"}",
					text.replace("\"", "\\\""));

			String response = restTemplate.postForObject(url, requestBody, String.class);
			JsonNode jsonResponse = objectMapper.readTree(response);
			return jsonResponse.get("translatedText").asText();

		} catch (Exception e) {
			System.err.println("LibreTranslate failed: " + e.getMessage());
			return translateCommonCookingTerms(text);
		}
	}

	private String translateCommonCookingTerms(String text) {
		String translated = text.replaceAll("(?i)fenugreek|methi", "मेथी").replaceAll("(?i)spinach|palak", "पालक")
				.replaceAll("(?i)potato|aloo", "बटाटा").replaceAll("(?i)onion|pyaz", "कांदा")
				.replaceAll("(?i)tomato|tamatar", "टोमॅटो").replaceAll("(?i)ginger", "आले")
				.replaceAll("(?i)garlic", "लसूण").replaceAll("(?i)green chili", "हिरवी मिरची")
				.replaceAll("(?i)wash", "धुवा").replaceAll("(?i)chop|cut", "चिरा").replaceAll("(?i)heat", "गरम करा")
				.replaceAll("(?i)add", "घाला").replaceAll("(?i)cook", "शिजवा").replaceAll("(?i)fry", "तळा")
				.replaceAll("(?i)boil", "उकडा").replaceAll("(?i)mix|stir", "मिक्स करा")
				.replaceAll("(?i)cover", "झाकण ठेवा").replaceAll("(?i)serve", "सर्व्ह करा").replaceAll("(?i)oil", "तेल")
				.replaceAll("(?i)salt", "मीठ").replaceAll("(?i)turmeric", "हळद").replaceAll("(?i)cumin|jeera", "जिरे")
				.replaceAll("(?i)mustard", "मोहरी").replaceAll("(?i)water", "पाणी")
				.replaceAll("(?i)yogurt|curd", "दही");

		return translated;
	}

	public String[] translateBatch(String[] texts) {
		String[] translations = new String[texts.length];
		for (int i = 0; i < texts.length; i++) {
			translations[i] = translateToMarathi(texts[i]);
		}
		return translations;
	}
}