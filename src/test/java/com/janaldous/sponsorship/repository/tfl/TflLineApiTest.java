package com.janaldous.sponsorship.repository.tfl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.tfl.dto.TflApiPresentationEntitiesAdditionalProperties;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesLine;
import com.janaldous.tfl.dto.TflApiPresentationEntitiesStopPoint;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Import({ TflApiTestConfig.class })
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Slf4j
class TflLineApiTest {

	@Autowired
	private TflLineApi tflLineApi;

	private Function<TflApiPresentationEntitiesStopPoint, String> mapToCSVRow = stop -> {
		String zone = stop.getAdditionalProperties().stream().filter(prop -> "Zone".equals(prop.getKey()))
				.map(TflApiPresentationEntitiesAdditionalProperties::getValue).findFirst().orElse(null);
		String address = stop.getAdditionalProperties().stream().filter(prop -> "Address".equals(prop.getKey()))
				.map(TflApiPresentationEntitiesAdditionalProperties::getValue).findFirst().orElse(null);

		String name = stop.getCommonName();
		String postcode = null;
		try {
			if (address != null) {
				postcode = TflAddressUtil.getPostCodeDistrict(address);
			}
		} catch (IllegalArgumentException e) {		
			log.warn(e.getMessage());
		}
		return MessageFormat.format("{0},{1},\"{2}\",{3}", name, zone, address, postcode);
	};

	@Disabled
	@Test
	// make sure to only run this once, since there were manual corrections to the CSV
	void testCallApi_thenWriteCSVToFile() throws IOException {
		List<TflApiPresentationEntitiesLine> result = tflLineApi.getLinesByMode("Regular", "tube", "dlr");
		assertNotNull(result);
		assertTrue(result.size() > 1);
		log.info("tube lines: " + result.size());
		String lineIds = result.stream().map(TflApiPresentationEntitiesLine::getId).collect(Collectors.joining(","));
		log.info("line ids: " + lineIds);

		Set<String> csv = result.stream().map(TflApiPresentationEntitiesLine::getId).map(lineId -> {
			List<TflApiPresentationEntitiesStopPoint> stops = tflLineApi.getStopsInLine(lineId, false);
			log.info(MessageFormat.format("lineId = {0} no of stops = {1}", lineId, stops.size()));
			return stops.stream().map(mapToCSVRow).collect(Collectors.toSet());
		}).flatMap(Set::stream).collect(Collectors.toSet());

		csv.forEach(System.out::println);

		writeToFile("src/main/resources/db/changelog/csv/stations2.csv", csv,
				new String[] { "Station Name", "Zone", "Address", "Post Code District" });
	}

	private void writeToFile(String filename, Collection<String> lines, String[] headers) throws IOException {
		try (FileWriter writer = new FileWriter(filename)) {
			// header
			writer.write(Arrays.stream(headers).collect(Collectors.joining(",")) + System.lineSeparator());

			// content
			for (String str : lines) {
				writer.write(str + System.lineSeparator());
			}
		}
	}

}
