package com.janaldous.sponsorship.dto.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TubeStationDto {

	private Long id;
	
	private String stationName;
	
	private String address;
	
	private String postCodeDistrict;
	
	private Integer zone;
	
}
