package com.momhelp.controller;

import com.momhelp.dto.AuthResponseDTO;
import com.momhelp.dto.LoginRequestDTO;
import com.momhelp.dto.SignupRequestDTO;
import com.momhelp.dto.UserResponseDTO;
import com.momhelp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
		try {
			AuthResponseDTO response = userService.signup(signupRequestDTO);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "User registered successfully!");
			responseBody.put("data", response);
			return ResponseEntity.ok(responseBody);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		try {
			AuthResponseDTO response = userService.login(loginRequestDTO);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Login successful!");
			responseBody.put("data", response);
			return ResponseEntity.ok(responseBody);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createErrorResponse("Invalid username/email or password"));
		}
	}

	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile(Authentication authentication) {
		try {
			String username = authentication.getName();
			UserResponseDTO user = userService.getUserProfile(username);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", user);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Auth endpoint is working!");
		return ResponseEntity.ok(response);
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}