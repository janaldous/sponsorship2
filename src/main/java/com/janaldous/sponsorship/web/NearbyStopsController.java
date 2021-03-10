package com.janaldous.sponsorship.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.dto.model.TrainStation;
import com.janaldous.sponsorship.service.NearbyStopsService;

@RestController
public class NearbyStopsController {

	@Autowired
	private NearbyStopsService nearbyStopsService;
	
	@GetMapping("/nearbystops")
	public List<TrainStation> getNearbyStops(@RequestParam String postCode) {
		return nearbyStopsService.getNearbyStops(postCode);
	}
	
}
