package com.momhelp.repository;

import com.momhelp.entity.User;
import com.momhelp.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VegetableRepository extends JpaRepository<Vegetable, Long> {

	List<Vegetable> findByUser(User user);

	List<Vegetable> findByUserAndNameContainingIgnoreCase(User user, String name);

	@Query("SELECT v FROM Vegetable v WHERE v.user = :user AND v.useBeforeDate >= CURRENT_DATE AND v.isSpoiled = false ORDER BY v.useBeforeDate ASC")
	List<Vegetable> findAvailableVegetablesByUser(User user);

	@Query("SELECT v FROM Vegetable v WHERE v.user = :user AND (v.useBeforeDate < CURRENT_DATE OR v.isSpoiled = true) ORDER BY v.useBeforeDate DESC")
	List<Vegetable> findSpoiledVegetablesByUser(User user);

	boolean existsByUserAndNameAndAddedDate(User user, String name, Date addedDate);
}
