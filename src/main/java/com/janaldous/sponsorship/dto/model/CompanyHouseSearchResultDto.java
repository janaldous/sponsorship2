package com.janaldous.sponsorship.dto.model;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanyHouseSearchResultDto {

	private String companyNumber;
	
	private String companyName;
	
	private AddressDto address;
	
	private String etag;
	
	private List<String> previousCompanyNames;
	
}
