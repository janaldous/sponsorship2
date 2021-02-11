package com.janaldous.sponsorship.repository.companyhouseapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.janaldous.sponsorship.config.CompanyHouseApiConfig;

@TestConfiguration
@Import(CompanyHouseApiConfig.class)
@ComponentScan("com.janaldous.sponsorship.repository.companyhouseapi")
public class CompanyHouseApiTestConfig {

}
