package com.janaldous.sponsorship.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.domain.CompanyHouseEntry;
import com.janaldous.sponsorship.dto.model.AddressDto;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiTestConfig;
import com.janaldous.sponsorship.service.CompanyHouseSearchService;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Import({CompanyHouseSearchService.class, CompanyHouseApiTestConfig.class, DtoMapperTestConfig.class})
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
@Slf4j
class CompanySearchResultMapperIT {
	
	@Autowired
	private CompanyHouseSearchService companyHouseSearchService;

	@Disabled
	@Test
	void testGetOneResult() throws CompanyHouseApiException {
		List<CompanyHouseSearchResultDto> results = companyHouseSearchService.findByCompanyName("behavox");
		assertEquals(1, results.size());
		CompanyHouseSearchResultDto result = results.get(0);
		AddressDto address = result.getAddress();
		assertEquals("29 Albert Embankment", address.getAddressLine1());
		assertNull(address.getAddressLine2());
		assertEquals("England", address.getCountry());
		assertEquals("Lambeth", address.getLocality());
		assertEquals("SE1 7GR", address.getPostCode());
		assertEquals("Merano", address.getPremises());
		assertNotNull(result.getCompanyNumber());
	}
	
	@Test
	void testGetOneResult2() throws CompanyHouseApiException {
		List<CompanyHouseSearchResultDto> results = companyHouseSearchService.findByCompanyName("wirewax ltd");
		assertEquals(1, results.size());
		CompanyHouseSearchResultDto result = results.get(0);
		log.info(result.toString());
		assertNotNull(result.getCompanyNumber());
	}
	
	@Test
	void testMapToEntity() {
		CompanyHouseSearchResultDto input = new CompanyHouseSearchResultDto();
		input.setAddress(new AddressDto());
		input.setCompanyNumber("123123123");
		CompanyHouseEntry result = CompanySearchResultMapper.toCompanyHouseEntry(input);
		
		assertEquals(123123123, result.getCompanyNumber());
	}
	
	@Disabled
	@Test
	void testGetNullResult() throws CompanyHouseApiException {
		List<CompanyHouseSearchResultDto> result = companyHouseSearchService.findByCompanyName("fdslkjfdsa");
		assertTrue(result.isEmpty());
	}
	
}
