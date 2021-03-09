package com.janaldous.sponsorship.dto.model;

import java.time.LocalDateTime;
import java.util.List;

import com.janaldous.sponsorship.domain.core.ApplicationMethod;
import com.janaldous.sponsorship.domain.core.ApplicationStatus;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;
import com.janaldous.sponsorship.domain.core.Technology;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class JobApplicationDto {
	
	@NonNull
	private Long id;
	
	@NonNull
	private LocalDateTime timestamp;
	
	@NonNull
	private Long companySponsorId;
	
	@NonNull
	private ApplicationMethod applicationMethod;
	
	@NonNull
	private ApplicationStatus status;
	
	private List<TechCompanyCategory> categories;
	
	private List<Technology> techStack;
	
	private String website;
	
	private String linkedInUrl;
	
	private String email;
	
	private String notes;

}
