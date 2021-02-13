package com.janaldous.sponsorship.dto.model;

import java.util.List;

import com.janaldous.sponsorship.domain.core.SIC;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanyHouseEntryDto {

	private Long id;

	private String companyNumber;
	
	private String companyName;
	
	private String etag;
	
	private String previousCompanyNames;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String addressCareOf;
	
	private String addressCountry;
	
	private String addressLocality;
	
	private String addressPOBox;
	
	private String addressPostCode;
	
	private String addressPremises;
	
	private String addressRegion;
	
	private List<SIC> sicCodes;
	
}
