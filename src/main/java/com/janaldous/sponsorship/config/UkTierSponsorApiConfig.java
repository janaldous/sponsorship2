package com.janaldous.sponsorship.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.janaldous.tfl.invoker.ApiClient;
import com.janaldous.uktiersponsors.api.CompaniesApi;

@Configuration
public class UkTierSponsorApiConfig {
	
	@Value("${uktiersponsor.api.url}")
	public String uktierSponsorApiUrl;
	
	@Autowired
	private Environment environment;

	@Bean
    public CompaniesApi companySearchApi() {
        return new CompaniesApi();
    }
	
	@Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(uktierSponsorApiUrl);
        apiClient.setDebugging(Arrays.asList(environment.getActiveProfiles()).contains("debug-apiclient"));
        return apiClient;
    }
	
}
