package com.janaldous.sponsorship.dto.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressDto {

	private String addressLine1;
	
	private String addressLine2;
	
	private String country;
	
	private String locality;
	
	private String postCode;
	
	private String premises;
	
}
