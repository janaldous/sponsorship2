package com.janaldous.sponsorship.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.dto.model.CompanySponsorDto;
import com.janaldous.sponsorship.dto.model.TubeStationDto;
import com.janaldous.sponsorship.repository.tfl.TflAddressUtil;
import com.janaldous.sponsorship.service.CompanySponsorService;
import com.janaldous.sponsorship.service.TubeStationService;
import com.janaldous.sponsorship.webdto.CompanySponsorDetailResponse;

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
	public Page<CompanySponsorDto> getCompanyHouseEntry(@RequestParam int page, @RequestParam int size,
			@RequestParam int zone) {
		return companySponsorService.getCompanySponsorsByTflZone(zone, PageRequest.of(page, size));
	}

	@GetMapping("/company/{id}")
	public CompanySponsorDetailResponse getCompanyHouseEntryById(@PathVariable Long id) {
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
