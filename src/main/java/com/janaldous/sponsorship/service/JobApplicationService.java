package com.janaldous.sponsorship.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.JobApplication;
import com.janaldous.sponsorship.dto.mapper.JobApplicationMapper;
import com.janaldous.sponsorship.dto.model.JobApplicationDto;
import com.janaldous.sponsorship.dto.model.JobApplicationEventDto;
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

	public JobApplicationDto handleJobApplicationEvent(JobApplicationEventDto jobApplicationEvent) {
		CompanySponsor companySponsor = companySponsorRepository
				.findById(jobApplicationEvent.getCompanySponsorId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Company sponsor was not found id = " + jobApplicationEvent));

		JobApplication jobApplication = JobApplicationMapper.toJobApplicationEntity(companySponsor, jobApplicationEvent);

		return JobApplicationMapper.toJobApplicationDto(jobApplicationRepository.save(jobApplication));
	}

	public List<JobApplicationDto> getApplicationByCompanySponsorId(Long companySponsorId) {
		List<JobApplication> jobApplications = jobApplicationRepository.findByCompanySponsor(companySponsorId);
		if (jobApplications.isEmpty()) {
			throw new ResourceNotFoundException(
					"Job application with company sponsor was not found id = " + companySponsorId);
		} else {
			return jobApplications.stream().map(JobApplicationMapper::toJobApplicationDto).collect(Collectors.toList());
		}
	}

}
