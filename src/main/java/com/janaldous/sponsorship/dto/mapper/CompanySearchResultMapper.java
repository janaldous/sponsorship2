package com.janaldous.sponsorship.dto.mapper;

import java.util.LinkedHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.companyhouse.dto.CompanySearchItems;
import com.janaldous.companyhouse.dto.RegisteredOfficeAddress;
import com.janaldous.sponsorship.domain.CompanyHouseEntry;
import com.janaldous.sponsorship.dto.model.AddressDto;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.exception.InternalServerException;

@Component
public class CompanySearchResultMapper {

	@Autowired
	private ObjectMapper objectMapper;
	
	public CompanyHouseSearchResultDto toCompanyHouseSearchResultDto(CompanySearchItems input) {
		CompanyHouseSearchResultDto output = new CompanyHouseSearchResultDto();
		output.setCompanyName(input.getTitle())
			.setCompanyNumber(input.getCompanyNumber())
			.setAddress(toAddressDto(input.getAddress()));
		return output;
	}

	public AddressDto toAddressDto(Object obj) {
		if (obj instanceof LinkedHashMap) {
			// openapi-client-generator did not generate code for RegisteredOfficeAddress well - it became LinkedHasMap
			RegisteredOfficeAddress address = objectMapper.convertValue(obj, RegisteredOfficeAddress.class);
			AddressDto output = new AddressDto();
			output.setAddressLine1(address.getAddressLine1())
				.setAddressLine2(address.getAddressLine2())
				.setPostCode(address.getPostalCode())
				.setCountry(address.getCountry() != null ? address.getCountry().toString() : null)
				.setLocality(address.getLocality())
				.setPremises(address.getPremises());
			return output;
		}
		throw new InternalServerException("Unexpected object type for address: " + obj.getClass());
	}
	
	public static CompanyHouseEntry toCompanyHouseEntry(CompanyHouseSearchResultDto input) {
		CompanyHouseEntry output = new CompanyHouseEntry();
		BeanUtils.copyProperties(input, output);
		AddressDto address = input.getAddress();
		output.setAddressLine1(address.getAddressLine1())
			.setAddressLine2(address.getAddressLine2())
			.setAddressLocality(address.getLocality())
			.setAddressPremises(address.getPremises())
			.setAddressPostCode(address.getPostCode())
			.setAddressCountry(address.getCountry());
		
		return output;
	}
}
