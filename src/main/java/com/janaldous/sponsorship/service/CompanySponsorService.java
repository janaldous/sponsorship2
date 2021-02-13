package com.janaldous.sponsorship.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.dto.mapper.CompanySponsorMapper;
import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;

@Service
public class CompanySponsorService {
	
	private CompanySponsorRepository companySponsorRepository;
	
	public CompanySponsorService(CompanySponsorRepository companySponsorRepository) {
		this.companySponsorRepository = companySponsorRepository;
	}

	public List<CompanySponsorDto> getCompanySponsors(String town, Pageable pageable) {
		return companySponsorRepository.findAllByTownAndLocalityAndNameMatches(town, pageable).stream()
				.map(CompanySponsorMapper::toCompanySponsorDto)
				.collect(Collectors.toList());
	}
	
	
}
