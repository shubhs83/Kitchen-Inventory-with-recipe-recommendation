package com.momhelp.service;

import com.momhelp.dto.MonthlyReportDTO;
import com.momhelp.dto.UsageHistoryDTO;
import java.util.List;

public interface UsageHistoryService {
	void recordUsage(String vegetableName, Double weightUsed, String unit, String dishName);

	List<UsageHistoryDTO> getAllHistory();

	MonthlyReportDTO getMonthlyReport(int year, int month);

	List<UsageHistoryDTO> getRecentHistory(int days);
}