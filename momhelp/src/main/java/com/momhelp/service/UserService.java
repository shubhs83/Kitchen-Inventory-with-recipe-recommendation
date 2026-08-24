package com.momhelp.service;

import com.momhelp.dto.AuthResponseDTO;
import com.momhelp.dto.LoginRequestDTO;
import com.momhelp.dto.SignupRequestDTO;
import com.momhelp.dto.UserResponseDTO;

public interface UserService {

	AuthResponseDTO signup(SignupRequestDTO signupRequestDTO);

	AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

	UserResponseDTO getUserProfile(String username);
}