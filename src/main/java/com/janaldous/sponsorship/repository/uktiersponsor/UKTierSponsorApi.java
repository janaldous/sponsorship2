package com.janaldous.sponsorship.repository.uktiersponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import com.janaldous.uktiersponsors.api.CompaniesApi;
import com.janaldous.uktiersponsors.dto.CompanySearchRequest;
import com.janaldous.uktiersponsors.dto.CompanySearchResponse;

@Repository
public class UKTierSponsorApi {

	private CompaniesApi companiesApi;
	
	@Autowired
	public UKTierSponsorApi(CompaniesApi companiesApi) {
		this.companiesApi = companiesApi;
	}
	
	public CompanySearchResponse searchCompanies(String companyName, String industry, String town) throws UKTierSponsorApiException {
		Integer itemsPerPage = 10;
		Integer startIndex = 0;
		try {
			CompanySearchRequest companySearchRequest = new CompanySearchRequest();
			companySearchRequest.company(companyName).industry(industry).town(town).pageNumber(startIndex).rowsPerPage(itemsPerPage);
			return companiesApi.comanySearchGET(companySearchRequest);
		} catch (RestClientException e) {
			throw new UKTierSponsorApiException(e);
		}
	}
	
}
