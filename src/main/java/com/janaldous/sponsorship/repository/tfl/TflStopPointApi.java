package com.janaldous.sponsorship.repository.tfl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.janaldous.tfl.api.StopPointApi;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesStopPointsResponse;

@Repository
public class TflStopPointApi {

	private StopPointApi stopPointApi;

	@Autowired
	public TflStopPointApi(StopPointApi stopPointApi) {
		this.stopPointApi = stopPointApi;
	}

	public TflApiPresentationEntitiesStopPointsResponse getStopPointByRadius(Pair<BigDecimal, BigDecimal> coordinates) {
		List<String> stopTypes = Arrays.asList("NaptanMetroStation", "NaptanRailStation");
		Double longitude = coordinates.getValue0().doubleValue();
		Double latitude = coordinates.getValue1().doubleValue();
		Integer radius = 1000;
		List<String> modes = Arrays.asList("tube", "dlr", "overground");
		return stopPointApi.stopPointGetByGeoPoint(stopTypes, latitude, longitude, radius, false,
				modes, null, null);
	}

}
