package com.janaldous.sponsorship.service;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import com.janaldous.sponsorship.dto.model.CompanyHouseSearchResultDto;

public class CompanyHouseMultipleResultFilter {

	public static final Predicate<CompanyHouseSearchResultDto> filterByLocality(@NonNull String town) {
		return (CompanyHouseSearchResultDto result) -> result.getAddress() != null ? town.equals(result.getAddress().getLocality()) : false;
	}
	
}
