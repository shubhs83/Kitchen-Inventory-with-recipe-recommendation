package com.momhelp.controller;

import com.momhelp.dto.MonthlyReportDTO;
import com.momhelp.dto.UsageHistoryDTO;
import com.momhelp.service.UsageHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usage-history")
@CrossOrigin(origins = "http://localhost:3000")
public class UsageHistoryController {

	@Autowired
	private UsageHistoryService usageHistoryService;

	// Get all history
	@GetMapping("/all")
	public ResponseEntity<List<UsageHistoryDTO>> getAllHistory() {
		return ResponseEntity.ok(usageHistoryService.getAllHistory());
	}

	// Get monthly report
	@GetMapping("/monthly-report")
	public ResponseEntity<?> getMonthlyReport(@RequestParam int year, @RequestParam int month) {
		try {
			MonthlyReportDTO report = usageHistoryService.getMonthlyReport(year, month);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("success", false);
			error.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// Get recent history (last N days)
	@GetMapping("/recent/{days}")
	public ResponseEntity<List<UsageHistoryDTO>> getRecentHistory(@PathVariable int days) {
		return ResponseEntity.ok(usageHistoryService.getRecentHistory(days));
	}
}