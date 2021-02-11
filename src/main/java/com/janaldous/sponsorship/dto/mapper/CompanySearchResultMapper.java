package com.janaldous.sponsorship.dto.mapper;

import java.util.LinkedHashMap;

import org.springframework.beans.BeanUtils;

import com.janaldous.companyhouse.dto.CompanySearchItems;
import com.janaldous.companyhouse.dto.RegisteredOfficeAddress;
import com.janaldous.sponsorship.dto.model.AddressDto;
import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;
import com.janaldous.sponsorship.exception.InternalServerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanySearchResultMapper {

	public static CompanyHouseSearchResultDto toCompanyHouseSearchResultDto(CompanySearchItems input) {
		CompanyHouseSearchResultDto output = new CompanyHouseSearchResultDto();
		output.setCompanyName(input.getTitle())
			.setCompanyNumber(input.getCompanyNumber())
			.setAddress(toAddressDto(input.getAddress()));
		return output;
	}

	public static AddressDto toAddressDto(Object obj) {
		if (obj instanceof RegisteredOfficeAddress) {
			RegisteredOfficeAddress address = (RegisteredOfficeAddress) obj;
			AddressDto output = new AddressDto();
			BeanUtils.copyProperties(address, output);
			return output;			
		} else if (obj instanceof LinkedHashMap) {
			LinkedHashMap<String, String> address = (LinkedHashMap<String, String>) obj;
			AddressDto output = new AddressDto();
			output.setAddressLine1(address.get("addressLine1"));
			log.debug("address object keyset " + address.keySet());
			return output;
		}
		throw new InternalServerException("Unexpected object type for address: " + obj.getClass());
	}
	
}
