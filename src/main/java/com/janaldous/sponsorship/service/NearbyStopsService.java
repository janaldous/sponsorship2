package com.janaldous.sponsorship.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.sponsorship.dto.model.TrainStation;
import com.janaldous.sponsorship.repository.postcodesio.PostCodesIoApi;
import com.janaldous.sponsorship.repository.tfl.TflStopPointApi;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesStopPointsResponse;

@Service
public class NearbyStopsService {
	
	private TflStopPointApi tflStopPointApi;
	
	private PostCodesIoApi postCodesIoApi;
	
	@Autowired
	public NearbyStopsService(TflStopPointApi tflStopPointApi, PostCodesIoApi postCodesIoApi) {
		this.tflStopPointApi = tflStopPointApi;
		this.postCodesIoApi = postCodesIoApi;
	}
	
	public List<TrainStation> getNearbyStops(String postCode) {
		Pair<BigDecimal,BigDecimal> coordinate = postCodesIoApi.getCoordinate(postCode);
		TflApiPresentationEntitiesStopPointsResponse result = tflStopPointApi.getStopPointByRadius(coordinate);
		return result.getStopPoints().stream().map(stopPoint -> {
			Long distance = Math.round(stopPoint.getDistance());
			String zone = stopPoint.getAdditionalProperties().stream().filter(prop -> "Zone".equals(prop.getKey())).findAny().map(prop -> prop.getValue()).orElse(null);
			String modes = stopPoint.getModes().stream().collect(Collectors.joining(","));
			String lines = stopPoint.getLines().stream().map(x -> x.getId()).collect(Collectors.joining(","));
			return TrainStation.builder()
					.name(stopPoint.getCommonName())
					.zone(zone)
					.modes(modes)
					.lines(lines)
					.distance(distance)
					.build();
		}).collect(Collectors.toList());
	}

}
