package com.momhelp.service.impl;

import com.momhelp.dto.ExpiryAlertRequestDTO;
import com.momhelp.dto.ExpiryAlertResponseDTO;
import com.momhelp.entity.ExpiryAlert;
import com.momhelp.entity.Vegetable;
import com.momhelp.repository.ExpiryAlertRepository;
import com.momhelp.repository.VegetableRepository;
import com.momhelp.service.EmailService;
import com.momhelp.service.ExpiryAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpiryAlertServiceImpl implements ExpiryAlertService {

	@Autowired
	private ExpiryAlertRepository expiryAlertRepository;

	@Autowired
	private VegetableRepository vegetableRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public ExpiryAlertResponseDTO createAlert(ExpiryAlertRequestDTO requestDTO) {
		ExpiryAlert alert = new ExpiryAlert();
		alert.setUserId(requestDTO.getUserId());
		alert.setVegetableId(requestDTO.getVegetableId());
		alert.setVegetableName(requestDTO.getVegetableName());
		alert.setExpiryDate(requestDTO.getExpiryDate());
		alert.setAlertType(requestDTO.getAlertType());
		alert.setDaysUntilExpiry(requestDTO.getDaysUntilExpiry());
		alert.setIsNotified(false);
		alert.setEmailSent(false);

		ExpiryAlert saved = expiryAlertRepository.save(alert);
		return convertToDTO(saved);
	}

	@Override
	public List<ExpiryAlertResponseDTO> getAllAlerts(Long userId) {
		List<ExpiryAlert> alerts = expiryAlertRepository.findByUserId(userId);
		return alerts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ExpiryAlertResponseDTO> getUnnotifiedAlerts(Long userId) {
		List<ExpiryAlert> alerts = expiryAlertRepository.findByUserIdAndIsNotified(userId, false);
		return alerts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ExpiryAlertResponseDTO> getAlertsByType(Long userId, String alertType) {
		List<ExpiryAlert> alerts = expiryAlertRepository.findByUserIdAndAlertType(userId, alertType);
		return alerts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public ExpiryAlertResponseDTO markAsNotified(Long alertId) {
		ExpiryAlert alert = expiryAlertRepository.findById(alertId)
				.orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));

		alert.setIsNotified(true);
		alert.setNotificationSentDate(new Date());

		ExpiryAlert updated = expiryAlertRepository.save(alert);
		return convertToDTO(updated);
	}

	@Override
	public void generateAlertsForExpiringVegetables(Long userId, String userEmail) {
		// Get all vegetables from repository
		List<Vegetable> vegetables = vegetableRepository.findAll();
		Date today = new Date();

		for (Vegetable veg : vegetables) {
			if (veg.getUseBeforeDate() != null && !veg.isSpoiled()) {
				long diffInMillis = veg.getUseBeforeDate().getTime() - today.getTime();
				int daysUntilExpiry = (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

				String alertType;
				if (daysUntilExpiry < 0) {
					alertType = "EXPIRED";
				} else if (daysUntilExpiry == 0) {
					alertType = "EXPIRING_TODAY";
				} else if (daysUntilExpiry <= 3) {
					alertType = "EXPIRING_SOON";
				} else {
					continue; // Skip if more than 3 days
				}

				ExpiryAlertRequestDTO requestDTO = new ExpiryAlertRequestDTO();
				requestDTO.setUserId(userId); // Use the userId from parameter
				requestDTO.setVegetableId(veg.getId());
				requestDTO.setVegetableName(veg.getName()); // Use getName() not getVegetableName()
				requestDTO.setExpiryDate(veg.getUseBeforeDate()); // Use useBeforeDate as expiry
				requestDTO.setAlertType(alertType);
				requestDTO.setDaysUntilExpiry(daysUntilExpiry);

				createAlert(requestDTO);
			}
		}
	}

	@Override
	public void sendEmailNotifications(Long userId, String userEmail) {
		List<ExpiryAlert> unsentAlerts = expiryAlertRepository.findByUserIdAndEmailSent(userId, false);

		if (unsentAlerts.isEmpty()) {
			return;
		}

		Map<String, List<ExpiryAlert>> groupedAlerts = unsentAlerts.stream()
				.collect(Collectors.groupingBy(ExpiryAlert::getAlertType));

		List<String> expiringSoonList = new ArrayList<>();
		List<String> expiredList = new ArrayList<>();

		for (Map.Entry<String, List<ExpiryAlert>> entry : groupedAlerts.entrySet()) {
			String alertType = entry.getKey();
			List<ExpiryAlert> alerts = entry.getValue();

			if ("EXPIRED".equals(alertType)) {
				for (ExpiryAlert alert : alerts) {
					expiredList.add(alert.getVegetableName());
					alert.setEmailSent(true);
					expiryAlertRepository.save(alert);
				}
			} else {
				for (ExpiryAlert alert : alerts) {
					expiringSoonList.add(alert.getVegetableName() + " (" + alert.getDaysUntilExpiry() + " days)");
					alert.setEmailSent(true);
					expiryAlertRepository.save(alert);
				}
			}
		}

		if (!expiringSoonList.isEmpty()) {
			emailService.sendBulkExpiryAlert(userEmail, expiringSoonList);
		}

		if (!expiredList.isEmpty()) {
			emailService.sendExpiredAlert(userEmail, expiredList);
		}
	}

	@Override
	public void deleteAlert(Long alertId) {
		expiryAlertRepository.deleteById(alertId);
	}

	@Override
	public void deleteAlertsByVegetableId(Long vegetableId) {
		expiryAlertRepository.deleteByVegetableId(vegetableId);
	}

	private ExpiryAlertResponseDTO convertToDTO(ExpiryAlert entity) {
		ExpiryAlertResponseDTO dto = new ExpiryAlertResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setVegetableId(entity.getVegetableId());
		dto.setVegetableName(entity.getVegetableName());
		dto.setExpiryDate(entity.getExpiryDate());
		dto.setAlertType(entity.getAlertType());
		dto.setDaysUntilExpiry(entity.getDaysUntilExpiry());
		dto.setIsNotified(entity.getIsNotified());
		dto.setNotificationSentDate(entity.getNotificationSentDate());
		dto.setEmailSent(entity.getEmailSent());
		dto.setCreatedDate(entity.getCreatedDate());
		return dto;
	}
}