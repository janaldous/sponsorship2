package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.location.TubeStation;

@Repository
public interface TubeStationRepository extends JpaRepository<TubeStation, Long> {

	@Query("SELECT t FROM TubeStation t WHERE t.postCodeDistrict = :postCodeDistrict")
	List<TubeStation> findAllByPostCodeDistrict(@Param("postCodeDistrict") String postCodeDistrict);
	
}
