package com.janaldous.sponsorship.dto.model;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.NonNull;

@Data
public class CompanySponsorResultDto {
	
	@NonNull
	private Page<CompanySponsorDto> page;
	
	@NonNull
	private long checkedCompanies;
	
}
