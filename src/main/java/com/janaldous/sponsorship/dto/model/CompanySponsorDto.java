package com.janaldous.sponsorship.dto.model;

import java.util.Date;

import com.janaldous.sponsorship.domain.FetchDataStatus;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanySponsorDto {
	
	private Long id;
	
	private PDFSponsorDto pdfSponsor;
	
	private CompanyHouseEntryDto companyHouseEntry;
	
	private FetchDataStatus fetchDataStatus;
	
	private Date dateUpdated;

}
