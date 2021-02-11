package com.janaldous.sponsorship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.janaldous.sponsorship.config.CompanyHouseApiConfig;

@Import({CompanyHouseApiConfig.class})
@SpringBootApplication
public class SponsorshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(SponsorshipApplication.class, args);
	}

}
