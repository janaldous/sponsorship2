package com.janaldous.sponsorship.dto.model;

import java.time.LocalDateTime;

import com.janaldous.sponsorship.domain.core.ApplicationMethod;
import com.janaldous.sponsorship.domain.core.ApplicationStatus;
import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JobApplicationDto {
	
	private Long id;
	
	private LocalDateTime timestamp;
	
	private Long companySponsorId;
	
	private ApplicationMethod applicationMethod;
	
	private ApplicationStatus status;
	
	private TechCompanyCategory techCompanyType;
	
	private String website;
	
	private String email;

}
