package com.janaldous.sponsorship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.janaldous.companyhouse.api.CompanyProfileApi;
import com.janaldous.companyhouse.api.SearchApi;
import com.janaldous.companyhouse.invoker.ApiClient;

@Configuration
public class CompanyHouseApiConfig {
	
	@Value("${companyhouse.api.url}")
	public String companyHouseApiUrl;
	
	@Value("${companyhouse.api.apiKey}")
	public String companyHouseApiKey;

	@Bean
    public SearchApi petApi() {
        return new SearchApi(apiClient());
    }
	
	@Bean
	public CompanyProfileApi companyProfileApi() {
		return new CompanyProfileApi(apiClient());
	}
	
	@Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(companyHouseApiUrl);
        apiClient.addDefaultHeader("Authorization", companyHouseApiKey);
        return apiClient;
    }
	
}
