package com.janaldous.sponsorship.dto.mapper;

import org.springframework.beans.BeanUtils;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;
import com.janaldous.sponsorship.dto.model.CompanyHouseEntryDto;

public class CompanyHouseEntryMapper {

	public static CompanyHouseEntryDto toCompanyHouseEntryDto(CompanyHouseEntry input) {
		CompanyHouseEntryDto output = new CompanyHouseEntryDto();
		if (input != null) {
			BeanUtils.copyProperties(input, output);
		}
		return output;
	}

}
