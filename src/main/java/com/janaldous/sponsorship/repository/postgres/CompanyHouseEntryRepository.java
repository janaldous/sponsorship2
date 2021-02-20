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
			+ "JOIN TubeStation t ON t.postCode IS NOT NULL "
			+ "AND che.addressPostCode LIKE t.postCode || '%' "
			+ "WHERE t.zone = :zone "
			+ "AND t.postCode IS NOT NULL")
	Page<CompanyHouseEntry> findAllByTflZone(Integer zone, Pageable pageable);
	
}
