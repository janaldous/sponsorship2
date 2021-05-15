package com.janaldous.sponsorship.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.janaldous.tfl.api.LineApi;
import com.janaldous.tfl.api.StopPointApi;
import com.janaldous.tfl.invoker.ApiClient;

@Configuration
public class TflApiConfig {
	
	@Value("${tfl.api.url}")
	public String tflApiUrl;
	
	@Value("${tfl.api.primarykey}")
	public String primaryKey;
	
	@Value("${tfl.api.secondarykey}")
	public String secondaryKey;
	
	@Autowired
	private Environment environment;

	@Bean
    public LineApi lineApi(ApiClient apiClientTfl) {
        return new LineApi(apiClientTfl);
    }
	
	@Bean
	public StopPointApi stopPointApi(ApiClient apiClientTfl) {
		return new StopPointApi(apiClientTfl);
	}
	
	@Bean
    public ApiClient apiClientTfl() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(tflApiUrl);
        apiClient.setApiKey(primaryKey);
        apiClient.setApiKeyPrefix(secondaryKey);
        apiClient.setDebugging(Arrays.asList(environment.getActiveProfiles()).contains("debug-apiclient"));
        return apiClient;
    }
	
}
