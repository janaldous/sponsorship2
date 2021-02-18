package com.janaldous.sponsorship.repository.tfl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.janaldous.tfl.api.LineApi;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesLine;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesStopPoint;

@Repository
public class TflLineApi {

	private LineApi lineApi;
	
	@Autowired
	public TflLineApi(LineApi lineApi) {
		this.lineApi = lineApi;
	}
	
	public List<TflApiPresentationEntitiesLine> getLinesByMode(String serviceTypes, String ...modes) {
		return lineApi.lineRouteByMode(Arrays.asList(modes), Arrays.asList(serviceTypes));
	}
	
	public List<TflApiPresentationEntitiesStopPoint> getStopsInLine(String lineId, boolean tflOperatedNationalRailStationsOnly) {
		return lineApi.lineStopPoints(lineId, tflOperatedNationalRailStationsOnly);
	}
	
}
