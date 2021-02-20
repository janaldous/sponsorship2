package com.janaldous.sponsorship.repository.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;

@Repository
public interface CompanyHouseEntryRepository extends JpaRepository<CompanyHouseEntry, Long> {

	@Query("SELECT DISTINCT che "
			+ "FROM CompanyHouseEntry che "
			+ "JOIN TubeStation t ON t.postCodeDistrict IS NOT NULL "
			+ "AND che.addressPostCode LIKE t.postCodeDistrict || '%' "
			+ "WHERE t.zone = :zone "
			+ "AND t.postCodeDistrict IS NOT NULL "
			+ "GROUP BY t.zone")
	Page<CompanyHouseEntry> findAllByTflZone(Integer zone, Pageable pageable);
	
}
