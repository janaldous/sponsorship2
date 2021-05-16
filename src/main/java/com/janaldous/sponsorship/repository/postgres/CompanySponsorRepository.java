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
	
	@Query(value = "SELECT * "
			+ "FROM company_sponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdf_sponsor_id = ps.id "
			+ "WHERE :zone = any(cs.tfl_zones) "
			+ "AND (cs.name_matches is true "
			+ "OR cs.fuzzy_matches is true) "
			+ "ORDER BY ps.company_name", nativeQuery = true)
	Page<CompanySponsor> findAllByTflZoneAndNameMatches(@Param("zone") Integer zone, Pageable pageable);
	
	@Query(value = "SELECT * "
			+ "FROM company_sponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdf_sponsor_id = ps.id "
			+ "JOIN company_house_entry che "
			+ "ON cs.company_house_entry_id = che.id "
			+ "AND (cs.name_matches is true "
			+ "OR cs.fuzzy_matches is true) "
			+ "AND che.address_post_code like :postcode || '%' "
			+ "ORDER BY ps.company_name", nativeQuery = true)
	Page<CompanySponsor> findAllByPostCode(@Param("postcode") String postCode, Pageable pageable);

	@Query(value = "SELECT count(*) "
			+ "FROM company_sponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdf_sponsor_id = ps.id "
			+ "JOIN company_house_entry che "
			+ "ON cs.company_house_entry_id = che.id "
			+ "AND (cs.name_matches is true "
			+ "OR cs.fuzzy_matches is true) "
			+ "AND che.address_post_code like :postcode || '%' ", nativeQuery = true)
	long countAllByPostCode(@Param("postcode") String postCode);

	@Query(value = "SELECT COUNT(*) "
			+ "FROM company_sponsor cs "
			+ "JOIN PDFSponsor ps "
			+ "ON cs.pdf_sponsor_id = ps.id "
			+ "WHERE :zone = any(cs.tfl_zones) "
			+ "AND (cs.name_matches is true "
			+ "OR cs.fuzzy_matches is true) ", nativeQuery = true)
	long countAllByZone(Integer zone);
	
}
