package com.janaldous.sponsorship.repository.tfl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		String zone = stop.getAdditionalProperties().stream()
			.filter(prop -> "Zone".equals(prop.getKey()))
			.map(TflApiPresentationEntitiesAdditionalProperties::getValue)
			.findFirst().orElse(null);
		String address = stop.getAdditionalProperties().stream()
				.filter(prop -> "Address".equals(prop.getKey()))
				.map(TflApiPresentationEntitiesAdditionalProperties::getValue)
				.findFirst().orElse(null);;
		
		String name = stop.getCommonName();
		return MessageFormat.format("{0},{1},\"{2}\"", name, zone, address);
	};
	
	@Test
	void testCallApi_thenWriteCSVToFile() throws IOException {
		List<TflApiPresentationEntitiesLine> result = tflLineApi.getLinesByMode("Regular", "tube", "dlr");
		assertNotNull(result);
		assertTrue(result.size() > 1);
		log.info("tube lines: " + result.size());
		String lineIds = result.stream().map(TflApiPresentationEntitiesLine::getId).collect(Collectors.joining(","));
		log.info("line ids: " + lineIds);

		List<String> csv = result.stream()
			.map(TflApiPresentationEntitiesLine::getId)
			.map(lineId -> {
				List<TflApiPresentationEntitiesStopPoint> stops = tflLineApi.getStopsInLine(lineId, false);
				log.info(MessageFormat.format("lineId = {0} no of stops = {1}", lineId, stops.size()));
				return stops.stream().map(mapToCSVRow).collect(Collectors.toList());
			})
			.flatMap(List::stream)
			.collect(Collectors.toList());
		
		csv.forEach(System.out::println);
		
		writeToFile("src/main/resources/tfl/stations.json", csv);
	}

	private void writeToFile(String filename, List<String> lines) throws IOException {
		FileWriter writer = new FileWriter(filename); 
		for(String str: lines) {
		  writer.write(str + System.lineSeparator());
		}
		writer.close();
	}
	
}
