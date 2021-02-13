package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.FetchDataStatus;
import com.janaldous.sponsorship.domain.core.PDFSponsor;

@Repository
public interface PDFSponsorRepository extends JpaRepository<PDFSponsor, Long> {

	List<PDFSponsor> findAllByIndustry(String industry, Pageable pageable);
	
	@Query(value = "SELECT ps FROM PDFSponsor ps LEFT JOIN CompanySponsor cs ON cs.pdfSponsor = ps WHERE cs.pdfSponsor IS NULL AND ps.industry = :industry")
	List<PDFSponsor> findAllByIndustryAndNullFetchStatus(@Param("industry") String industry, Pageable pageable);
	
	@Query(value = "SELECT ps FROM PDFSponsor ps LEFT JOIN CompanySponsor cs ON cs.pdfSponsor = ps WHERE cs.fetchDataStatus = :fetchDataStatus")
	List<PDFSponsor> findAllByFetchStatus(@Param("fetchDataStatus") FetchDataStatus fetchDataStatus, Pageable pageable);
	
	@Query(value = "SELECT ps FROM PDFSponsor ps LEFT JOIN CompanySponsor cs ON cs.pdfSponsor = ps WHERE cs.fetchDataStatus = :fetchDataStatus AND cs.dateUpdated IS NULL")
	List<PDFSponsor> findAllByFetchStatusAndDateUpdatedIsNull(@Param("fetchDataStatus") FetchDataStatus fetchDataStatus, Pageable pageable);
	
	@Query(value = "SELECT ps FROM PDFSponsor ps LEFT JOIN CompanySponsor cs ON cs.pdfSponsor = ps JOIN CompanyHouseEntry che ON cs.companyHouseEntry = che WHERE cs.fetchDataStatus = :fetchDataStatus AND che.companyNumber IS NULL")
	List<PDFSponsor> findAllByFetchStatusAndEmptyCompanyNumber(@Param("fetchDataStatus") FetchDataStatus fetchDataStatus, Pageable pageable);
	
}
