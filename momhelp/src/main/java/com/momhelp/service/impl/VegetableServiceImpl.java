package com.momhelp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momhelp.dto.VegetableRequestDTO;
import com.momhelp.dto.VegetableResponseDTO;
import com.momhelp.entity.User;
import com.momhelp.entity.Vegetable;
import com.momhelp.repository.UserRepository;
import com.momhelp.repository.VegetableRepository;
import com.momhelp.service.UsageHistoryService;
import com.momhelp.service.VegetableService;

@Service
@Transactional
public class VegetableServiceImpl implements VegetableService {

	@Autowired
	private VegetableRepository vegetableRepository;

	@Autowired
	private UsageHistoryService usageHistoryService;

	@Autowired
	private UserRepository userRepository;

	// 🔴 GET LOGGED IN USER
	private User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found: " + username));
	}

	@Override
	public VegetableResponseDTO addVegetable(VegetableRequestDTO dto) {
		User user = getCurrentUser();

		if (vegetableRepository.existsByUserAndNameAndAddedDate(user, dto.getName(), dto.getAddedDate())) {
			throw new RuntimeException("Vegetable with same name and date already exists!");
		}

		Vegetable vegetable = new Vegetable();
		vegetable.setName(dto.getName());
		vegetable.setWeight(dto.getWeight());
		vegetable.setUnit(dto.getUnit());
		vegetable.setAddedDate(dto.getAddedDate());
		vegetable.setUseBeforeDate(dto.getUseBeforeDate());
		vegetable.setUser(user); // 🔴 IMPORTANT

		if (vegetable.getUseBeforeDate().before(new Date())) {
			vegetable.setSpoiled(true);
		}

		return convertToDTO(vegetableRepository.save(vegetable));
	}

	@Override
	public VegetableResponseDTO updateVegetable(Long id, VegetableRequestDTO dto) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		vegetable.setName(dto.getName());
		vegetable.setWeight(dto.getWeight());
		vegetable.setUnit(dto.getUnit());
		vegetable.setAddedDate(dto.getAddedDate());
		vegetable.setUseBeforeDate(dto.getUseBeforeDate());

		vegetable.setSpoiled(vegetable.getUseBeforeDate().before(new Date()));

		return convertToDTO(vegetableRepository.save(vegetable));
	}

	@Override
	public void deleteVegetable(Long id) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		vegetableRepository.delete(vegetable);
	}

	@Override
	public VegetableResponseDTO getVegetableById(Long id) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		return convertToDTO(vegetable);
	}

	@Override
	public List<VegetableResponseDTO> getAllVegetables() {
		User user = getCurrentUser();
		return vegetableRepository.findByUser(user).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<VegetableResponseDTO> searchVegetablesByName(String name) {
		User user = getCurrentUser();
		return vegetableRepository.findByUserAndNameContainingIgnoreCase(user, name).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<VegetableResponseDTO> getAvailableVegetables() {
		User user = getCurrentUser();
		return vegetableRepository.findAvailableVegetablesByUser(user).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<VegetableResponseDTO> getSpoiledVegetables() {
		User user = getCurrentUser();
		return vegetableRepository.findSpoiledVegetablesByUser(user).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public VegetableResponseDTO markAsSpoiled(Long id) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		vegetable.setSpoiled(true);
		return convertToDTO(vegetableRepository.save(vegetable));
	}

	@Override
	public void removeSpoiledVegetable(Long id) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		if (!vegetable.isSpoiled() && !vegetable.getUseBeforeDate().before(new Date())) {
			throw new RuntimeException("Vegetable is not spoiled!");
		}

		vegetableRepository.delete(vegetable);
	}

	@Override
	public VegetableResponseDTO useVegetable(Long id, Double weightUsed) {
		User user = getCurrentUser();

		Vegetable vegetable = vegetableRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vegetable not found"));

		if (!vegetable.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		if (vegetable.isSpoiled() || vegetable.getUseBeforeDate().before(new Date())) {
			throw new RuntimeException("Cannot use spoiled vegetable!");
		}

		if (vegetable.getWeight() < weightUsed) {
			throw new RuntimeException("Not enough quantity!");
		}

		double newWeight = vegetable.getWeight() - weightUsed;

		// ✅ SAME METHOD — NO BREAKING
		usageHistoryService.recordUsage(vegetable.getName(), weightUsed, vegetable.getUnit(), null);

		if (newWeight <= 0) {
			vegetableRepository.delete(vegetable);
			return null;
		} else {
			vegetable.setWeight(newWeight);
			return convertToDTO(vegetableRepository.save(vegetable));
		}
	}

	private VegetableResponseDTO convertToDTO(Vegetable v) {
		return new VegetableResponseDTO(v.getId(), v.getName(), v.getWeight(), v.getUnit(), v.getAddedDate(),
				v.getUseBeforeDate(), v.isSpoiled());
	}
}
