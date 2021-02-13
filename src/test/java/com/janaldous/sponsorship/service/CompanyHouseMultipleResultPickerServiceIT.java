package com.janaldous.sponsorship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.janaldous.sponsorship.domain.core.FetchDataStatus;
import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.dto.mapper.PDFSponsorMapper;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

@SpringBootTest
@ActiveProfiles("debug-apiclient")
class CompanyHouseMultipleResultPickerServiceIT {

	@Autowired
	private CompanyHouseSearchService companyHouseSearchService;
	
	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@Test
	void test() throws CompanyHouseApiException {
		List<PDFSponsor> sponsors = pdfSponsorRepository.findAllByFetchStatus(FetchDataStatus.MULTIPLE_RESULT, PageRequest.of(0, 1));
		PDFSponsor sponsor = sponsors.get(0);
		List<CompanyHouseSearchResultDto> searchResults = companyHouseSearchService.findByCompanyName(sponsor.getCompanyName());
		List<CompanyHouseSearchResultDto> filteredResults = searchResults.stream().filter(CompanyHouseMultipleResultFilter.filterByLocality(sponsor.getTown())).collect(Collectors.toList());
		assertEquals(1, filteredResults.size());
	}

}
