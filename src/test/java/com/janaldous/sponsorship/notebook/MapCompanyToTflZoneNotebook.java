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
class MapCompanyToTflZoneNotebook {

	@Autowired
	private CompanyHouseSearchService companyHouseSearchService;

	@Autowired
	private CompanySponsorRepository companySponsorRepository;

	@Autowired
	private FuzzyComparator<CompanyHouseSearchResultDto> fuzzyComparator;

	private NameNormalizer nameNormalizer = new NameNormalizer();

	@Test
	@Commit
	void test() throws CompanyHouseApiException {
		
	}

}
