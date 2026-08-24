package com.momhelp.repository;

import com.momhelp.entity.ExpiryAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpiryAlertRepository extends JpaRepository<ExpiryAlert, Long> {

	List<ExpiryAlert> findByUserId(Long userId);

	List<ExpiryAlert> findByUserIdAndIsNotified(Long userId, Boolean isNotified);

	List<ExpiryAlert> findByUserIdAndAlertType(Long userId, String alertType);

	List<ExpiryAlert> findByUserIdAndEmailSent(Long userId, Boolean emailSent);

	List<ExpiryAlert> findByExpiryDateBefore(Date date);

	void deleteByVegetableId(Long vegetableId);
}