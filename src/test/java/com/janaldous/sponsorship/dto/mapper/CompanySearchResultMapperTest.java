package com.janaldous.sponsorship.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiTestConfig;
import com.janaldous.sponsorship.service.CompanyHouseSearchService;

@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class, CompanyHouseApiTestConfig.class})
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
class CompanySearchResultMapperTest {
	
	@Autowired
	private CompanyHouseSearchService companyHouseSearchService;

	@Test
	void testGetOneResult() {
		List<CompanyHouseSearchResultDto> result = companyHouseSearchService.findByCompanyName("behavox");
		assertEquals(1, result.size());
	}
	
}
