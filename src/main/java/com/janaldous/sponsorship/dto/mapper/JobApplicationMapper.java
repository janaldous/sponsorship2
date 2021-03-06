package com.janaldous.sponsorship.dto.mapper;

import com.janaldous.sponsorship.domain.core.JobApplication;
import com.janaldous.sponsorship.dto.model.JobApplicationDto;

public class JobApplicationMapper {
	
	public static JobApplicationDto toJobApplicationDto(JobApplication input) {
		JobApplicationDto output = new JobApplicationDto();
		output.setId(input.getId())
			.setApplicationMethod(input.getApplicationMethod())
			.setStatus(input.getStatus())
			.setTechCompanyType(input.getTechCompanyType())
			.setTimestamp(input.getTimestamp())
			.setCompanySponsorId(input.getCompanySponsor().getId())
			.setEmail(input.getEmail())
			.setWebsite(input.getWebsite());
		return output;
	}

}
