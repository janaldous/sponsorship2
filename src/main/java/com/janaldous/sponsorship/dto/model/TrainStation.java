package com.janaldous.sponsorship.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainStation {

	@NonNull
	private String name;
	
	private String zone;
	
	private String modes;
	
	private String lines;
	
	private Long distance;
	
}
