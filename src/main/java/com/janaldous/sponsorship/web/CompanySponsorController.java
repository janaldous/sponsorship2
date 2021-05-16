package com.janaldous.sponsorship.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.dto.model.CompanySponsorResultDto;
import com.janaldous.sponsorship.dto.model.TubeStationDto;
import com.janaldous.sponsorship.exception.ValidationException;
import com.janaldous.sponsorship.repository.tfl.TflAddressUtil;
import com.janaldous.sponsorship.service.CompanySponsorService;
import com.janaldous.sponsorship.service.TubeStationService;
import com.janaldous.sponsorship.webdto.model.CompanySponsorDetailResponse;

@RestController
public class CompanySponsorController {

	private CompanySponsorService companySponsorService;

	private TubeStationService tubeStationService;

	@Autowired
	public CompanySponsorController(CompanySponsorService companySponsorService,
			TubeStationService tubeStationService) {
		this.companySponsorService = companySponsorService;
		this.tubeStationService = tubeStationService;
	}

	@GetMapping("/company")
	public CompanySponsorResultDto getCompanySponsor(@RequestParam int page, 
			@RequestParam int size,
			@RequestParam(required = false) Integer zone,
			@RequestParam(required = false) String postcode,
			@RequestParam(required = false) String companyName) {
		
		PageRequest pageRequest = PageRequest.of(page, size);
		if (companyName != null) {
			return companySponsorService.getCompanySponsorsByCompanyName(companyName, pageRequest);
		} else if (zone != null) {
			return companySponsorService.getCompanySponsorsByTflZone(zone, pageRequest);
		} else if (postcode != null) {
			return companySponsorService.getCompanySponsorsByPostCode(postcode, pageRequest);
		} else {
			throw new ValidationException("zone or postcode must not be empty");
		}
	}

	@GetMapping("/company/{id}")
	public CompanySponsorDetailResponse getCompanySponsorById(@PathVariable Long id) {
		CompanySponsorDetailResponse response = new CompanySponsorDetailResponse();
		CompanySponsorDto companySponsor = companySponsorService.getCompanySponsorById(id);
		response.setCompanySponsor(companySponsor);

		String postCode = companySponsor.getCompanyHouseEntry().getAddressPostCode();
		if (postCode != null) {
			String postCodeDistrict = TflAddressUtil.getPostCodeDistrictFromPostCode(postCode);

			List<TubeStationDto> nearbyTubeStations = tubeStationService
					.findAllTubeStationByPostCodeDistrict(postCodeDistrict);
			response.setNearbyTubeStations(nearbyTubeStations);
		}

		return response;
	}

}
