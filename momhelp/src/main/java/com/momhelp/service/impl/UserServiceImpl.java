package com.momhelp.service.impl;

import com.momhelp.dto.AuthResponseDTO;
import com.momhelp.dto.LoginRequestDTO;
import com.momhelp.dto.SignupRequestDTO;
import com.momhelp.dto.UserResponseDTO;
import com.momhelp.entity.User;
import com.momhelp.repository.UserRepository;
import com.momhelp.security.JwtUtil;
import com.momhelp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public AuthResponseDTO signup(SignupRequestDTO signupRequestDTO) {
		if (userRepository.existsByUsername(signupRequestDTO.getUsername())) {
			throw new RuntimeException("Username already exists!");
		}

		if (userRepository.existsByEmail(signupRequestDTO.getEmail())) {
			throw new RuntimeException("Email already exists!");
		}

		User user = new User();
		user.setUsername(signupRequestDTO.getUsername());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
		user.setFullName(signupRequestDTO.getFullName());
		user.setPhoneNumber(signupRequestDTO.getPhoneNumber());

		User savedUser = userRepository.save(user);

		String token = jwtUtil.generateToken(savedUser.getUsername());

		return new AuthResponseDTO(token, savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(),
				savedUser.getFullName());
	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequestDTO.getUsernameOrEmail(), loginRequestDTO.getPassword()));

		User user = userRepository.findByUsername(loginRequestDTO.getUsernameOrEmail())
				.orElseGet(() -> userRepository.findByEmail(loginRequestDTO.getUsernameOrEmail())
						.orElseThrow(() -> new RuntimeException("User not found")));

		String token = jwtUtil.generateToken(user.getUsername());

		return new AuthResponseDTO(token, user.getId(), user.getUsername(), user.getEmail(), user.getFullName());
	}

	@Override
	public UserResponseDTO getUserProfile(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(),
				user.getPhoneNumber(), user.getCreatedDate(), user.getIsActive());
	}
}