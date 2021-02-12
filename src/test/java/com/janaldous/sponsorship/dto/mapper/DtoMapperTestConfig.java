package com.janaldous.sponsorship.dto.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;

@TestConfiguration
@ComponentScan("com.janaldous.sponsorship.dto.mapper")
public class DtoMapperTestConfig {
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
