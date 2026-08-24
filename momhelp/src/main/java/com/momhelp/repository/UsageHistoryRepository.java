package com.momhelp.repository;

import com.momhelp.entity.UsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistory, Long> {

	// Get history for specific month and year
	@Query("SELECT u FROM UsageHistory u WHERE YEAR(u.usedDate) = :year AND MONTH(u.usedDate) = :month ORDER BY u.usedDate DESC")
	List<UsageHistory> findByMonthAndYear(int year, int month);

	// Get recent history (last N days)
	@Query("SELECT u FROM UsageHistory u WHERE u.usedDate >= :startDate ORDER BY u.usedDate DESC")
	List<UsageHistory> findRecentHistory(Date startDate);

	// Get all history
	List<UsageHistory> findAllByOrderByUsedDateDesc();
}