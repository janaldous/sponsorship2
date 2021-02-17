package com.janaldous.sponsorship.repository.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class TubeStationLocationTest {

	@Test
	void testReadGeoJson() throws IOException {
		TubeStationLocation tubeStationLocation = new TubeStationLocation();
		List<String> result = tubeStationLocation.readTubeStations();

		assertFalse(result.isEmpty());
		
		result.forEach(System.out::println);
	}

	@Test
	void testGetPostCode() {
		String address = "Acton Town Station, London Underground Ltd., Gunnersbury Lane, London, W3 8HN";
		String postCode = TubeStationLocation.getPostCode(address);
		assertEquals("W3 8HN", postCode);
	}
	
	@Test
	void testGetPostCodeDistrict() {
		String address = "W3 8HN";
		String postCode = TubeStationLocation.getPostCodeDistrict(address);
		assertEquals("W3", postCode);
	}
	
	@Test
	void testGetPostCodeDistrictInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			String address = "W38HN";
			TubeStationLocation.getPostCodeDistrict(address);
		});
	}
}
