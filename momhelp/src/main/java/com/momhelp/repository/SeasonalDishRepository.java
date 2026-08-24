package com.momhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.momhelp.entity.SeasonalDish;

@Repository
public interface SeasonalDishRepository extends JpaRepository<SeasonalDish, Long> {

    // Get dishes by season
    List<SeasonalDish> findBySeason(String season);

    @Query(
    	    value = "SELECT * FROM seasonal_dishes WHERE season = ?1 ORDER BY RAND() LIMIT 5",
    	    nativeQuery = true
    	)
    	List<SeasonalDish> findRandomBySeason(String season, int limit);

}
