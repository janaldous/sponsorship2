package com.janaldous.sponsorship.dto.mapper;

import org.springframework.beans.BeanUtils;

import com.janaldous.sponsorship.domain.CompanyHouseEntry;
import com.janaldous.sponsorship.dto.model.CompanyHouseEntryDto;

public class CompanyHouseEntryMapper {
	
	public static CompanyHouseEntryDto toCompanyHouseEntryDto(CompanyHouseEntry input) {
		CompanyHouseEntryDto output = new CompanyHouseEntryDto();
		BeanUtils.copyProperties(input, output);
		return output;
	}

}
