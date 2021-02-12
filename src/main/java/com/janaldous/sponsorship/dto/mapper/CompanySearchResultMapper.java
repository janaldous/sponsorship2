package com.janaldous.sponsorship.dto.mapper;

import java.util.LinkedHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.janaldous.companyhouse.dto.CompanySearchItems;
import com.janaldous.sponsorship.domain.CompanyHouseEntry;
import com.janaldous.sponsorship.dto.model.AddressDto;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.exception.InternalServerException;

@Component
public class CompanySearchResultMapper {

	public CompanyHouseSearchResultDto toCompanyHouseSearchResultDto(CompanySearchItems input) {
		CompanyHouseSearchResultDto output = new CompanyHouseSearchResultDto();
		output.setCompanyName(input.getTitle())
			.setCompanyNumber(input.getCompanyNumber())
			.setAddress(toAddressDto(input.getAddress()));
		return output;
	}

	public AddressDto toAddressDto(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof LinkedHashMap) {
			// openapi-client-generator did not generate code for RegisteredOfficeAddress well - it became LinkedHasMap
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, String> address = (LinkedHashMap<String, String>) obj;
			AddressDto output = new AddressDto();
			output.setAddressLine1(address.get("address_line_1"))
				.setAddressLine2(address.get("address_line_2"))
				.setPostCode(address.get("postal_code"))
				.setCountry(address.get("country"))
				.setLocality(address.get("locality"))
				.setPremises(address.get("premises"));
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
