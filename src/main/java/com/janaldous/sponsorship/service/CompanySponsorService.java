package com.janaldous.sponsorship.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.dto.mapper.CompanySponsorMapper;
import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.dto.model.CompanySponsorResultDto;
import com.janaldous.sponsorship.exception.ResourceNotFoundException;
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

	public CompanySponsorResultDto getCompanySponsorsByTflZone(Integer zone, Pageable pageable) {
		Page<CompanySponsorDto> page = companySponsorRepository.findAllByTflZoneAndNameMatches(zone, pageable)
				.map(CompanySponsorMapper::toCompanySponsorDto);
		
		long checkedCompanies = companySponsorRepository.countAllByZone(zone);
		
		return new CompanySponsorResultDto(page, checkedCompanies);
	}

	public CompanySponsorDto getCompanySponsorById(Long id) {
		return companySponsorRepository.findById(id)
				.map(CompanySponsorMapper::toCompanySponsorDto)
				.orElseThrow(() -> new ResourceNotFoundException("Company sponsor was not found. id = " + id));
	}

	public CompanySponsorResultDto getCompanySponsorsByPostCode(String postcode, PageRequest pageRequest) {
		Page<CompanySponsorDto> page = companySponsorRepository.findAllByPostCode(postcode, pageRequest)
				.map(CompanySponsorMapper::toCompanySponsorDto);
		
		long checkedCompanies = companySponsorRepository.countAllByPostCode(postcode);
		
		return new CompanySponsorResultDto(page, checkedCompanies);
	}

	public CompanySponsorResultDto getCompanySponsorsByCompanyName(String companyName, PageRequest pageRequest) {
		Page<CompanySponsorDto> page = companySponsorRepository.findAllByCompanyName(companyName, pageRequest)
				.map(CompanySponsorMapper::toCompanySponsorDto);
		
		long checkedCompanies = companySponsorRepository.countAllByCompanyName(companyName);
		
		return new CompanySponsorResultDto(page, checkedCompanies);
	}

}
