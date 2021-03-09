package com.janaldous.sponsorship.repository.tfl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.tfl.dto.TflApiPresentationEntitiesStopPointsResponse;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Import({ TflApiTestConfig.class })
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Slf4j
class TflStopPointApiTest {

	@Autowired
	private TflStopPointApi tflStopPointApi;
	
	@Test
	void test() {
		Pair<BigDecimal, BigDecimal> coordinates = new Pair<>(new BigDecimal("-0.161524"), new BigDecimal("51.464689"));
		TflApiPresentationEntitiesStopPointsResponse result = tflStopPointApi.getStopPointByRadius(coordinates);
		log.info(result.toString());
		assertNotNull(result);
	}

}
