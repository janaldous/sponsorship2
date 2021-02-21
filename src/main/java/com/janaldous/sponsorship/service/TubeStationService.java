package com.janaldous.sponsorship.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.dto.mapper.TubeStationMapper;
import com.janaldous.sponsorship.dto.model.TubeStationDto;
import com.janaldous.sponsorship.repository.postgres.TubeStationRepository;

@Service
public class TubeStationService {

	private TubeStationRepository tubeStationRepository;
	
	@Autowired
	public TubeStationService(TubeStationRepository tubeStationRepository) {
		this.tubeStationRepository = tubeStationRepository;
	}

	public List<TubeStationDto> findAllTubeStationByPostCodeDistrict(String postCodeDistrict) {
		return tubeStationRepository.findAllByPostCodeDistrict(postCodeDistrict).stream()
				.map(TubeStationMapper::toTubeStationDto)
				.collect(Collectors.toList());
	}

}
