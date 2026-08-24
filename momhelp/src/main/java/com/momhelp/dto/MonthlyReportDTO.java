package com.momhelp.dto;

import java.util.List;
import java.util.Map;

public class MonthlyReportDTO {
	private int month;
	private int year;
	private String monthName;
	private List<UsageHistoryDTO> usageHistory;
	private Map<String, Double> vegetableUsageSummary; // vegetableName -> totalWeight
	private Map<String, Integer> dishFrequency; // dishName -> count
	private Double totalWeightUsed;
	private int totalTransactions;

	// Constructors
	public MonthlyReportDTO() {
	}

	// Getters and Setters
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public List<UsageHistoryDTO> getUsageHistory() {
		return usageHistory;
	}

	public void setUsageHistory(List<UsageHistoryDTO> usageHistory) {
		this.usageHistory = usageHistory;
	}

	public Map<String, Double> getVegetableUsageSummary() {
		return vegetableUsageSummary;
	}

	public void setVegetableUsageSummary(Map<String, Double> vegetableUsageSummary) {
		this.vegetableUsageSummary = vegetableUsageSummary;
	}

	public Map<String, Integer> getDishFrequency() {
		return dishFrequency;
	}

	public void setDishFrequency(Map<String, Integer> dishFrequency) {
		this.dishFrequency = dishFrequency;
	}

	public Double getTotalWeightUsed() {
		return totalWeightUsed;
	}

	public void setTotalWeightUsed(Double totalWeightUsed) {
		this.totalWeightUsed = totalWeightUsed;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}
}