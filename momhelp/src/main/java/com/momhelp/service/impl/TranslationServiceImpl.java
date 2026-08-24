package com.momhelp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.momhelp.service.TranslationService;

@Service
public class TranslationServiceImpl implements TranslationService {

	private final RestTemplate restTemplate = new RestTemplate();
	private static final String TRANSLATE_URL = "https://libretranslate.com/translate";

	@Override
	public String translate(String text, String targetLang) {
		try {
			Map<String, Object> body = new HashMap<>();
			body.put("q", text);
			body.put("source", "en");
			body.put("target", targetLang);
			body.put("format", "text");

			Map response = restTemplate.postForObject(TRANSLATE_URL, body, Map.class);
			return response.get("translatedText").toString();

		} catch (Exception e) {
			return text; // fallback to English
		}
	}
}
