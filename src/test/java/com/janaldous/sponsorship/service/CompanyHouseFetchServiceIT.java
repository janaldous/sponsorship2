package com.janaldous.sponsorship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.janaldous.sponsorship.domain.FetchDataStatus;
import com.janaldous.sponsorship.domain.PDFSponsor;
import com.janaldous.sponsorship.dto.mapper.PDFSponsorMapper;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

@SpringBootTest
@ActiveProfiles("debug-apiclient")
class CompanyHouseFetchServiceIT {

	@Autowired
	private CompanyHouseFetchService companyHouseFetchService;
	
	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@Test
	void test() {
		List<PDFSponsor> computerProgrammingSponsors = pdfSponsorRepository.findAllByFetchStatusAndDateUpdatedIsNull(FetchDataStatus.MORE_THAN_ONE, PageRequest.of(0, 600));
		int noOfSponsors = computerProgrammingSponsors.size();
		companyHouseFetchService.fetchAndSaveCompanyHouseData(
				computerProgrammingSponsors.stream().map(PDFSponsorMapper::toPDFSponsorDto).collect(Collectors.toList()));
		
		assertEquals(10, noOfSponsors);
	}

}
