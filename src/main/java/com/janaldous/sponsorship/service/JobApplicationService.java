package com.janaldous.sponsorship.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.JobApplication;
import com.janaldous.sponsorship.dto.mapper.JobApplicationMapper;
import com.janaldous.sponsorship.dto.model.JobApplicationCreateDto;
import com.janaldous.sponsorship.dto.model.JobApplicationDto;
import com.janaldous.sponsorship.exception.ResourceNotFoundException;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.repository.postgres.JobApplicationRepository;

@Service
public class JobApplicationService {

	private JobApplicationRepository jobApplicationRepository;
	
	private CompanySponsorRepository companySponsorRepository;

	@Autowired
	public JobApplicationService(JobApplicationRepository jobApplicationRepository,
			CompanySponsorRepository companySponsorRepository) {
		this.jobApplicationRepository = jobApplicationRepository;
		this.companySponsorRepository = companySponsorRepository;
	}

	public JobApplicationDto getApplicationById(Long id) {
		return jobApplicationRepository.findById(id)
				.map(JobApplicationMapper::toJobApplicationDto)
				.orElseThrow(() -> new ResourceNotFoundException("Job application was not found. id = " + id));
	}
	
	public JobApplicationDto createApplication(JobApplicationCreateDto jobApplicationCreateRequest) {
		CompanySponsor companySponsor = companySponsorRepository.findById(jobApplicationCreateRequest.getCompanySponsorId())
			.orElseThrow(() -> new ResourceNotFoundException("Company sponsor was not found id = " + jobApplicationCreateRequest));
		
		JobApplication jobApplication = new JobApplication();
		jobApplication.setCompanySponsor(companySponsor)
			.setTimestamp(LocalDateTime.now())
			.setApplicationMethod(jobApplicationCreateRequest.getApplicationMethod())
			.setStatus(jobApplicationCreateRequest.getStatus())
			.setTechCompanyType(jobApplicationCreateRequest.getTechCompanyType())
			.setWebsite(jobApplicationCreateRequest.getWebsite())
			.setEmail(jobApplicationCreateRequest.getEmail());
		
		return JobApplicationMapper.toJobApplicationDto(jobApplicationRepository.save(jobApplication));
	}

	public JobApplicationDto getApplicationByCompanySponsorId(Long companySponsorId) {
		return jobApplicationRepository.findByCompanySponsor(companySponsorId)
			.map(JobApplicationMapper::toJobApplicationDto)
			.orElseThrow(() -> new ResourceNotFoundException("Job application with company sponsor was not found id = " + companySponsorId));
	}

}
