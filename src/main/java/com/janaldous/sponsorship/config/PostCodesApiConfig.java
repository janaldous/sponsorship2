package com.janaldous.sponsorship.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.janaldous.postcodesio.api.PostCodesApi;
import com.janaldous.tfl.invoker.ApiClient;

@Configuration
public class PostCodesApiConfig {
	
	@Value("${postcodes.api.url}")
	public String postCodesApiUrl;
	
	@Autowired
	private Environment environment;
	
	@Bean
	public PostCodesApi postCodesApi() {
		return new PostCodesApi();
	}

	@Bean
    public ApiClient apiClientPostcodesio() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(postCodesApiUrl);
        apiClient.setDebugging(Arrays.asList(environment.getActiveProfiles()).contains("debug-apiclient"));
        return apiClient;
    }
	
}
