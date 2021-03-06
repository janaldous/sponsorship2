package com.janaldous.sponsorship.webdto.model;

import java.util.List;

import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.dto.model.TubeStationDto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanySponsorDetailResponse {

	private CompanySponsorDto companySponsor;
	
	private List<TubeStationDto> nearbyTubeStations;
	
}
