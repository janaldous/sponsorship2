package com.janaldous.sponsorship.service;

import org.springframework.data.domain.Page;
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

	public Page<CompanySponsorDto> getCompanySponsorsByLocality(String town, Pageable pageable) {
		return companySponsorRepository.findAllByTownAndLocalityAndNameMatches(town, pageable)
				.map(CompanySponsorMapper::toCompanySponsorDto);
	}
	
	public Page<CompanySponsorDto> getCompanySponsorsByTflZone(Integer zone, Pageable pageable) {
		return companySponsorRepository.findAllByTflZoneAndNameMatches(zone, pageable)
				.map(CompanySponsorMapper::toCompanySponsorDto);
	}
	
}
