package com.janaldous.sponsorship.repository.companyhouseapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import com.janaldous.companyhouse.api.SearchApi;
import com.janaldous.companyhouse.dto.CompanySearch;

@Repository
public class CompanyHouseSearchApi {

	private SearchApi searchApi;
	
	@Autowired
	public CompanyHouseSearchApi(SearchApi searchApi) {
		this.searchApi = searchApi;
	}
	
	public CompanySearch searchAPI(String query) throws CompanyHouseApiException {
		Integer itemsPerPage = 10;
		Integer startIndex = 0;
		try {
			return searchApi.searchCompaniesGet(query, itemsPerPage, startIndex);
		} catch (RestClientException e) {
			throw new CompanyHouseApiException(e);
		}
	}
	
}
