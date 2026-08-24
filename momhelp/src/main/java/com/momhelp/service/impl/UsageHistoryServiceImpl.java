package com.momhelp.service.impl;

import com.momhelp.dto.MonthlyReportDTO;
import com.momhelp.dto.UsageHistoryDTO;
import com.momhelp.entity.UsageHistory;
import com.momhelp.repository.UsageHistoryRepository;
import com.momhelp.service.UsageHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsageHistoryServiceImpl implements UsageHistoryService {

	@Autowired
	private UsageHistoryRepository usageHistoryRepository;

	@Override
	public void recordUsage(String vegetableName, Double weightUsed, String unit, String dishName) {
		UsageHistory history = new UsageHistory(vegetableName, weightUsed, unit, new Date(), dishName);
		usageHistoryRepository.save(history);
	}

	@Override
	public List<UsageHistoryDTO> getAllHistory() {
		return usageHistoryRepository.findAllByOrderByUsedDateDesc().stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public MonthlyReportDTO getMonthlyReport(int year, int month) {
		List<UsageHistory> history = usageHistoryRepository.findByMonthAndYear(year, month);

		MonthlyReportDTO report = new MonthlyReportDTO();
		report.setMonth(month);
		report.setYear(year);
		report.setMonthName(getMonthName(month));
		report.setUsageHistory(history.stream().map(this::convertToDTO).collect(Collectors.toList()));
		report.setTotalTransactions(history.size());

		// Calculate vegetable usage summary
		Map<String, Double> vegSummary = new HashMap<>();
		double totalWeight = 0;

		for (UsageHistory h : history) {
			vegSummary.put(h.getVegetableName(),
					vegSummary.getOrDefault(h.getVegetableName(), 0.0) + h.getWeightUsed());
			totalWeight += h.getWeightUsed();
		}

		report.setVegetableUsageSummary(vegSummary);
		report.setTotalWeightUsed(totalWeight);

		// Calculate dish frequency
		Map<String, Integer> dishFreq = new HashMap<>();
		for (UsageHistory h : history) {
			if (h.getDishName() != null && !h.getDishName().isEmpty()) {
				dishFreq.put(h.getDishName(), dishFreq.getOrDefault(h.getDishName(), 0) + 1);
			}
		}
		report.setDishFrequency(dishFreq);

		return report;
	}

	@Override
	public List<UsageHistoryDTO> getRecentHistory(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -days);
		Date startDate = cal.getTime();

		return usageHistoryRepository.findRecentHistory(startDate).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private UsageHistoryDTO convertToDTO(UsageHistory history) {
		return new UsageHistoryDTO(history.getId(), history.getVegetableName(), history.getWeightUsed(),
				history.getUnit(), history.getUsedDate(), history.getDishName());
	}

	private String getMonthName(int month) {
		String[] months = { "", "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return months[month];
	}
}