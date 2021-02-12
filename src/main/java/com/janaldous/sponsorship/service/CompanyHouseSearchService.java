package com.janaldous.sponsorship.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.companyhouse.dto.CompanySearch;
import com.janaldous.sponsorship.dto.mapper.CompanySearchResultMapper;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseSearchApi;

@Service
public class CompanyHouseSearchService {

	private CompanyHouseSearchApi companyHouseSearchApi;
	
	private CompanySearchResultMapper companySearchResultMapper;
	
	@Autowired
	public CompanyHouseSearchService(CompanyHouseSearchApi companyHouseSearchApi,
			CompanySearchResultMapper companySearchResultMapper) {
		this.companyHouseSearchApi = companyHouseSearchApi;
		this.companySearchResultMapper = companySearchResultMapper;
	}
	
	public List<CompanyHouseSearchResultDto> findByCompanyName(String companyName) {
		CompanySearch result = companyHouseSearchApi.searchAPI(companyName);
		
		if (result.getItems() == null) return null;
		
		return result.getItems().stream()
				.map(x -> companySearchResultMapper.toCompanyHouseSearchResultDto(x))
				.collect(Collectors.toList());
	}
	
}
