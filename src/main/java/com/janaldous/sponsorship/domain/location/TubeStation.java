package com.janaldous.sponsorship.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class TubeStation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "station_name")
	private String stationName;
	
	@Column(name = "local_authority")
	private String localAuthority;
	
	@Column(name = "zone")
	private Integer zone;
	
}
