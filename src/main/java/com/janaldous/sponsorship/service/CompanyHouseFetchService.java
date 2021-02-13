package com.janaldous.sponsorship.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.domain.CompanyHouseEntry;
import com.janaldous.sponsorship.domain.CompanySponsor;
import com.janaldous.sponsorship.domain.FetchDataStatus;
import com.janaldous.sponsorship.dto.mapper.CompanySearchResultMapper;
import com.janaldous.sponsorship.dto.mapper.PDFSponsorMapper;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.dto.model.PDFSponsorDto;
import com.janaldous.sponsorship.exception.InternalServerException;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.postgres.CompanyHouseEntryRepository;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyHouseFetchService {

	private CompanyHouseSearchService companyHouseSearchService;

	private CompanySponsorRepository companySponsorRepository;

	private CompanyHouseEntryRepository companyHouseEntryRepository;

	@Autowired
	public CompanyHouseFetchService(CompanyHouseSearchService companyHouseSearchService,
			CompanySponsorRepository companySponsorRepository,
			CompanyHouseEntryRepository companyHouseEntryRepository) {
		this.companyHouseSearchService = companyHouseSearchService;
		this.companySponsorRepository = companySponsorRepository;
		this.companyHouseEntryRepository = companyHouseEntryRepository;
	}

	// TODO rename this method
	public void fetchAndSaveCompanyHouseData(List<PDFSponsorDto> list) {
		// single threaded approach
		list.forEach(pdfSponsor -> {
			// call api

			List<CompanyHouseSearchResultDto> results = null;
			try {
				results = companyHouseSearchService.findByCompanyName(pdfSponsor.getCompanyName());
			} catch (CompanyHouseApiException e) {
				log.info("Fetch failed for " + pdfSponsor.toString());
				log.info(e.toString() + "");
				throw new InternalServerException(e);
			}
			
			// save
			Optional<CompanySponsor> optionalCompanySponsor = companySponsorRepository.findAllByPdfSponsor(PDFSponsorMapper.toPDFSponsorEntity(pdfSponsor)).stream().findFirst();
			CompanySponsor companySponsor = optionalCompanySponsor.orElse(new CompanySponsor());
			companySponsor.setPdfSponsor(PDFSponsorMapper.toPDFSponsorEntity(pdfSponsor));
			// TODO remove me
			companySponsor.setDateUpdated(new Date());

			if (results.size() == 1) {
				CompanyHouseEntry companyHouseEntry = companySponsor.getCompanyHouseEntry();
				CompanyHouseEntry entry = CompanySearchResultMapper.toCompanyHouseEntry(results.get(0));
				if (companyHouseEntry == null) {
					companyHouseEntry = companyHouseEntryRepository.save(entry);
				} else if (companyHouseEntry.getCompanyNumber() == null) {
					companyHouseEntry.setCompanyNumber(entry.getCompanyNumber());
					companyHouseEntry = companyHouseEntryRepository.save(companyHouseEntry);
				}
				companySponsor.setCompanyHouseEntry(companyHouseEntry);
				companySponsor.setFetchDataStatus(FetchDataStatus.SUCCESS);
			} else if (results.isEmpty()) {
				companySponsor.setFetchDataStatus(FetchDataStatus.NO_RESULT);
			} else if (results.size() > 1) {
				results = results.stream().filter(CompanyHouseMultipleResultFilter.filterByLocality(pdfSponsor.getTown())).collect(Collectors.toList());
				if (results.size() == 1) {
					companySponsor.setFetchDataStatus(FetchDataStatus.MULTIPLE_RESULT_MATCH_LOCALITY);
				} else {
					companySponsor.setFetchDataStatus(FetchDataStatus.MULTIPLE_RESULT);					
				}
			}

			companySponsorRepository.save(companySponsor);
		});
	}

}
