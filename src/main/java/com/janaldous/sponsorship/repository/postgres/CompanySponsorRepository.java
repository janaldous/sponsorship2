package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.model.CompanySponsorZone;

@Repository
public interface CompanySponsorRepository extends JpaRepository<CompanySponsor, Long> {

	List<CompanySponsor> findAllByPdfSponsor(PDFSponsor pdfSponsor);
	
	@Query("SELECT cs "
			+ "FROM CompanySponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdfSponsor = ps "
			+ "JOIN CompanyHouseEntry che "
			+ "ON cs.companyHouseEntry = che "
			+ "WHERE :town IS NOT NULL "
			+ "AND (che.addressLocality LIKE %:town% OR ps.town LIKE %:town%)")
	List<CompanySponsor> findAllByTownAndLocality(@Param("town") String town, Pageable pageable);
	
	@Query("SELECT cs "
			+ "FROM CompanySponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdfSponsor = ps "
			+ "JOIN CompanyHouseEntry che "
			+ "ON cs.companyHouseEntry = che "
			+ "WHERE :town IS NOT NULL "
			+ "AND (che.addressLocality LIKE %:town% OR ps.town LIKE %:town%) "
			+ "AND cs.nameMatches = true")
	Page<CompanySponsor> findAllByTownAndLocalityAndNameMatches(@Param("town") String town, Pageable pageable);
	
	@Query("SELECT new com.janaldous.sponsorship.repository.postgres.model.CompanySponsorZone(cs, t.zone as zone) "
			+ "FROM CompanySponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdfSponsor = ps "
			+ "JOIN CompanyHouseEntry che "
			+ "ON cs.companyHouseEntry = che "
			+ "JOIN TubeStation t "
			+ "ON t.postCodeDistrict IS NOT NULL "
			+ "AND che.addressPostCode LIKE t.postCodeDistrict || '%' "
			+ "WHERE t.zone = :zone "
			+ "AND cs.nameMatches = true "
			+ "GROUP BY t.zone, cs.id")
	Page<CompanySponsorZone> findAllByTflZoneAndNameMatches(@Param("zone") Integer zone, Pageable pageable);

}
