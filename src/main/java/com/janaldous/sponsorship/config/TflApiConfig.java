package com.janaldous.sponsorship.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.janaldous.tfl.api.LineApi;
import com.janaldous.tfl.invoker.ApiClient;

@Configuration
public class TflApiConfig {
	
	@Value("${tfl.api.url}")
	public String tflApiUrl;
	
	@Autowired
	private Environment environment;

	@Bean
    public LineApi lineApi() {
        return new LineApi(apiClientTfl());
    }
	
	@Bean
    public ApiClient apiClientTfl() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(tflApiUrl);
        apiClient.setDebugging(Arrays.asList(environment.getActiveProfiles()).contains("debug-apiclient"));
        return apiClient;
    }
	
}
