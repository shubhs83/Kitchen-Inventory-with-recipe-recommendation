package com.momhelp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GroqConfig {

	// Groq API Configuration
	public static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

	// ⚠️ IMPORTANT: Get your FREE API key from https://console.groq.com/keys
	// Replace this with your actual API key
	public static final String GROQ_API_KEY = "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";

	// Model to use (llama-3.3-70b-versatile is fast and powerful)
	public static final String MODEL_NAME = "openai/gpt-oss-20b";
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}