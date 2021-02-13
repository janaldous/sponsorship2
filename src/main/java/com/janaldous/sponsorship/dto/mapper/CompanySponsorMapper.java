package com.janaldous.sponsorship.dto.mapper;

import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.dto.model.CompanySponsorDto;

public class CompanySponsorMapper {

	public static CompanySponsorDto toCompanySponsorDto(CompanySponsor input) {
		CompanySponsorDto output = new CompanySponsorDto();
		output.setId(input.getId())
			.setDateUpdated(input.getDateUpdated())
			.setFetchDataStatus(input.getFetchDataStatus())
			.setNameMatches(input.getNameMatches())
			.setLocalityMatches(input.getLocalityMatches())
			.setCompanyHouseEntry(CompanyHouseEntryMapper.toCompanyHouseEntryDto(input.getCompanyHouseEntry()))
			.setPdfSponsor(PDFSponsorMapper.toPDFSponsorDto(input.getPdfSponsor()));
		return output;
	}
	
}
