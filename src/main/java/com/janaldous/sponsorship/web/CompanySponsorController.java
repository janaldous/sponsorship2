package com.janaldous.sponsorship.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public List<CompanySponsorDto> getCompanyHouseEntry(@RequestParam int page, 
			@RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		return companySponsorService.getCompanySponsorService(pageable);
	}
	
}
