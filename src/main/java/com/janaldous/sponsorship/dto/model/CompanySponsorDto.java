package com.janaldous.sponsorship.dto.model;

import java.time.LocalDateTime;

import com.janaldous.sponsorship.domain.core.FetchDataStatus;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanySponsorDto {
	
	private Long id;
	
	private PDFSponsorDto pdfSponsor;
	
	private CompanyHouseEntryDto companyHouseEntry;
	
	private FetchDataStatus fetchDataStatus;
	
	private LocalDateTime dateUpdated;
	
	private Boolean nameMatches;
	
	private Boolean localityMatches;

}
