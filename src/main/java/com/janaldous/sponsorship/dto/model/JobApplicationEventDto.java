package com.janaldous.sponsorship.dto.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.janaldous.sponsorship.domain.core.ApplicationMethod;
import com.janaldous.sponsorship.domain.core.ApplicationStatus;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;
import com.janaldous.sponsorship.domain.core.Technology;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JobApplicationEventDto {

	@NotNull(message = "Company Sponsor id is mandatory")
	@NonNull
	private Long companySponsorId;

	@NonNull
	private ApplicationMethod applicationMethod;

	@NonNull
	private ApplicationStatus status;

	private List<TechCompanyCategory> categories;
	
	private List<Technology> techStack;

	private String website;

	private String email;

}
