package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.service.CompanySponsorService;

@RestController
public class CompanySponsorController {

	private CompanySponsorService companySponsorService;
	
	@Autowired
	public CompanySponsorController(CompanySponsorService companySponsorService) {
		this.companySponsorService = companySponsorService;
	}
	
	@GetMapping("/company")
	public Page<CompanySponsorDto> getCompanyHouseEntry(@RequestParam int page,
			@RequestParam int size,
			@RequestParam int zone) {
		return companySponsorService.getCompanySponsorsByTflZone(zone, PageRequest.of(page, size));
	}
	
}
