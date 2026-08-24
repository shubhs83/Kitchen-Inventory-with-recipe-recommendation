package com.momhelp.service;

import com.momhelp.dto.ExpiryAlertRequestDTO;
import com.momhelp.dto.ExpiryAlertResponseDTO;

import java.util.List;

public interface ExpiryAlertService {

	ExpiryAlertResponseDTO createAlert(ExpiryAlertRequestDTO requestDTO);

	List<ExpiryAlertResponseDTO> getAllAlerts(Long userId);

	List<ExpiryAlertResponseDTO> getUnnotifiedAlerts(Long userId);

	List<ExpiryAlertResponseDTO> getAlertsByType(Long userId, String alertType);

	ExpiryAlertResponseDTO markAsNotified(Long alertId);

	void generateAlertsForExpiringVegetables(Long userId, String userEmail);

	void sendEmailNotifications(Long userId, String userEmail);

	void deleteAlert(Long alertId);

	void deleteAlertsByVegetableId(Long vegetableId);
}