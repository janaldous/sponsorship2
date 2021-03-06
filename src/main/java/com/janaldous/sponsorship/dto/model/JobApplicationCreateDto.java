package com.janaldous.sponsorship.dto.model;

import com.janaldous.sponsorship.domain.core.ApplicationMethod;
import com.janaldous.sponsorship.domain.core.ApplicationStatus;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JobApplicationCreateDto {

	@NonNull
	private Long companySponsorId;

	@NonNull
	private ApplicationMethod applicationMethod;

	@NonNull
	private ApplicationStatus status;

	@NonNull
	private TechCompanyCategory techCompanyType;

	private String website;

	private String email;

}
