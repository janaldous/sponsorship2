package com.janaldous.sponsorship.dto.model;

import java.time.LocalDateTime;

import com.janaldous.sponsorship.domain.core.FetchDataStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CompanySponsorDto {
	
	@NonNull
	private Long id;
	
	@NonNull
	private PDFSponsorDto pdfSponsor;
	
	@NonNull
	private CompanyHouseEntryDto companyHouseEntry;
	
	@NonNull
	private FetchDataStatus fetchDataStatus;
	
	private LocalDateTime dateUpdated;
	
	private Boolean nameMatches;
	
	private Boolean localityMatches;
	
	private Boolean checked;

}
