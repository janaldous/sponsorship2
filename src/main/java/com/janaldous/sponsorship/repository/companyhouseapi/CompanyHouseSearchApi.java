package com.janaldous.sponsorship.repository.companyhouseapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.janaldous.companyhouse.api.SearchApi;
import com.janaldous.companyhouse.dto.CompanySearch;

@Repository
public class CompanyHouseSearchApi {

	private SearchApi searchApi;
	
	@Autowired
	public CompanyHouseSearchApi(SearchApi searchApi) {
		this.searchApi = searchApi;
	}
	
	public CompanySearch searchAPI(String query) {
		Integer itemsPerPage = 10;
		Integer startIndex = 0;
		return searchApi.searchCompaniesGet(query, itemsPerPage, startIndex);
	}
	
}
