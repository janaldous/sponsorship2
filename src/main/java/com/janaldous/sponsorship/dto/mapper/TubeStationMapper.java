package com.janaldous.sponsorship.dto.mapper;

import com.janaldous.sponsorship.domain.location.TubeStation;
import com.janaldous.sponsorship.dto.model.TubeStationDto;

public class TubeStationMapper {

	public static TubeStationDto toTubeStationDto(TubeStation input) {
		TubeStationDto output = new TubeStationDto();
		output.setAddress(input.getAddress())
			.setId(input.getId())
			.setPostCodeDistrict(input.getPostCodeDistrict())
			.setStationName(input.getStationName())
			.setZone(input.getZone());
		return output;
	}

}
