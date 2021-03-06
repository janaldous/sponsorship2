package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.exception.InternalServerException;
import com.janaldous.sponsorship.exception.ResourceNotFoundException;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.repository.uktiersponsor.UKTierSponsorApi;
import com.janaldous.sponsorship.repository.uktiersponsor.UKTierSponsorApiException;
import com.janaldous.uktiersponsors.dto.CompanySearchResponse;

@RestController
public class UKTierSponsorController {

	@Autowired
	private UKTierSponsorApi ukTierSponsorApi;
	
	@Autowired
	private CompanySponsorRepository companySponsorRepository;
	
	@GetMapping("/uktiersponsor")
	public CompanySearchResponse getUKTierSponsor(@RequestParam Long companySponsorId) {
		CompanySponsor companySponsor = companySponsorRepository.findById(companySponsorId)
			.orElseThrow(() -> new ResourceNotFoundException("Cannot find company sponsor with id = " + companySponsorId));
		try {
			return ukTierSponsorApi.searchCompanies(companySponsor.getCompanyHouseEntry().getCompanyName(), null, null);
		} catch (UKTierSponsorApiException e) {
			throw new InternalServerException(e);
		}
	}
	
}
