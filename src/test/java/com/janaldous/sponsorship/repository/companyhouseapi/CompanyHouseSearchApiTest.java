package com.janaldous.sponsorship.repository.companyhouseapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.companyhouse.dto.CompanySearch;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Import(CompanyHouseApiTestConfig.class)
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
@Slf4j
class CompanyHouseSearchApiTest {
	
	@Autowired
	private CompanyHouseSearchApi companyHouseSearchApi;

	@Test
	void test() {
		CompanySearch result = companyHouseSearchApi.searchAPI("behavox");
		log.debug(result.getTotalResults() + "");
		assertTrue(result.getTotalResults() >= 1);
	}
	
}
