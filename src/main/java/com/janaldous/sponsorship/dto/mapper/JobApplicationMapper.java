package com.janaldous.sponsorship.dto.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.JobApplication;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;
import com.janaldous.sponsorship.domain.core.Technology;
import com.janaldous.sponsorship.dto.model.JobApplicationDto;
import com.janaldous.sponsorship.dto.model.JobApplicationEventDto;

public class JobApplicationMapper {
	
	public static JobApplication toJobApplicationEntity(CompanySponsor companySponsor, JobApplicationEventDto input) {
		JobApplication output = new JobApplication();
		output.setCompanySponsor(companySponsor)
				.setTimestamp(LocalDateTime.now())
				.setApplicationMethod(input.getApplicationMethod())
				.setStatus(input.getStatus())
				.setWebsite(input.getWebsite())
				.setEmail(input.getEmail());

		if (input.getCategories() != null) {
			output.setCategories(input.getCategories()
					.stream()
					.map(TechCompanyCategory::toString)
					.toArray(String[]::new));
		}

		if (input.getTechStack() != null) {
			output.setTechStack(input.getTechStack()
					.stream()
					.map(Technology::toString)
					.toArray(String[]::new));
		}
		
		return output;
	}
	
	public static JobApplicationDto toJobApplicationDto(JobApplication input) {
		JobApplicationDto output = new JobApplicationDto();
		output.setId(input.getId())
			.setApplicationMethod(input.getApplicationMethod())
			.setStatus(input.getStatus())
			.setTimestamp(input.getTimestamp())
			.setCompanySponsorId(input.getCompanySponsor().getId())
			.setEmail(input.getEmail())
			.setWebsite(input.getWebsite());
		
		if (input.getCategories() != null) {
			output.setCategories(Arrays.stream(input.getCategories()).map(x -> TechCompanyCategory.valueOf(x)).collect(Collectors.toList()));
		}
		
		if (input.getTechStack() != null) {
			output.setTechStack(Arrays.stream(input.getTechStack()).map(x -> Technology.valueOf(x)).collect(Collectors.toList()));
		}
		
		return output;
	}

}
