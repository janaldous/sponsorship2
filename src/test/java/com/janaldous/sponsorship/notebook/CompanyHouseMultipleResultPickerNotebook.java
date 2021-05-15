package com.janaldous.sponsorship.notebook;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;
import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.FetchDataStatus;
import com.janaldous.sponsorship.dto.mapper.CompanySearchResultMapper;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.repository.postgres.util.FuzzyComparator;
import com.janaldous.sponsorship.repository.postgres.util.NameNormalizer;
import com.janaldous.sponsorship.service.CompanyHouseSearchService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class CompanyHouseMultipleResultPickerNotebook {

	@Autowired
	private CompanyHouseSearchService companyHouseSearchService;

	@Autowired
	private CompanySponsorRepository companySponsorRepository;

	@Autowired
	private FuzzyComparator<CompanyHouseSearchResultDto> fuzzyComparator;

	private NameNormalizer nameNormalizer = new NameNormalizer();

	@Test
//	@Transactional
	@Commit
	void test() throws CompanyHouseApiException {
		// for each company sponsor
		// if company sponsor is !nameMatches or is !fuzzyMatches then search company
		// house
		// if multiple result, use fuzzy logic to pick best result

		long count = companySponsorRepository.count();
		int page = 0;
		
		for (int i = 0; i < count;) {
			Page<CompanySponsor> findAll = companySponsorRepository.findAll(PageRequest.of(page, 100));

			List<CompanySponsor> companySponsors = findAll.get()
					.filter(x -> (x.getFuzzyMatches() == null || !x.getFuzzyMatches())
							&& (x.getNameMatches() == null || !x.getNameMatches()))
					.map(x -> {
						List<CompanyHouseSearchResultDto> companyHouseResults;
						String companyName = x.getPdfSponsor().getCompanyName();
						try {
							companyHouseResults = companyHouseSearchService.findByCompanyName(companyName);
						} catch (CompanyHouseApiException e) {
							throw new RuntimeException(e);
						}
						if (companyHouseResults.size() > 1) {
							if (nameNormalizer.normalize(companyHouseResults.get(0).getCompanyName())
									.equalsIgnoreCase(nameNormalizer.normalize(companyName))) {
								CompanyHouseSearchResultDto firstResult = companyHouseResults.get(0);
								log.info(companyName + " ==> "
										+ Optional.of(firstResult)
												.map(CompanyHouseSearchResultDto::getCompanyName)
												.orElse("nothing"));
								if (x.getCompanyHouseEntry() == null || !x.getCompanyHouseEntry().getCompanyNumber().equals(firstResult.getCompanyNumber())) {
									CompanyHouseEntry che = CompanySearchResultMapper.toCompanyHouseEntry(firstResult);
									che.setNormalizedName(nameNormalizer.normalize(che.getCompanyName()));
									x.setCompanyHouseEntry(che);
								}
								x.setNameMatches(true);
								x.setFetchDataStatus(FetchDataStatus.MULTIPLE_RESULT_MATCH_NORMALIZED);
								return x;
							}
						}
						Optional<CompanyHouseSearchResultDto> mainResult = fuzzyComparator.findMatching(
								nameNormalizer.normalize(companyName), companyHouseResults,
								y -> nameNormalizer.normalize(y.getCompanyName()));
						log.info(companyName + " ==> "
								+ mainResult.map(CompanyHouseSearchResultDto::getCompanyName).orElse("nothing"));

						if (mainResult.isPresent()) {
							if (x.getCompanyHouseEntry() == null || !x.getCompanyHouseEntry().getCompanyNumber().equals(mainResult.get().getCompanyNumber())) {
								CompanyHouseEntry che = CompanySearchResultMapper.toCompanyHouseEntry(mainResult.get());
								che.setNormalizedName(nameNormalizer.normalize(che.getCompanyName()));
								x.setCompanyHouseEntry(che);
							}
							x.setFuzzyMatches(true);
							x.setFetchDataStatus(FetchDataStatus.MULTIPLE_RESULT_MATCH_FUZZY);
						}

						return x;
					})
					.collect(Collectors.toList());

			companySponsorRepository.saveAll(companySponsors);

			log.info("number saved " + companySponsors.size());
			log.info("page " + page + " of " + findAll.getTotalPages());
			
			i += findAll.getSize();
			page++;
		}


	}

}
