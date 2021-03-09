package com.janaldous.sponsorship.repository.postcodesio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({PostCodesIoApiTestConfig.class})
class PostCodesIoApiTest {

	@Autowired
	private PostCodesIoApi postCodesIoApi;
	
	@Test
	void testGetCoordinate() {
		Pair<BigDecimal,BigDecimal> coordinate = postCodesIoApi.getCoordinate("SW11 5TG");
		assertEquals("-0.161524", coordinate.getValue0().toString());
		assertEquals("51.464689", coordinate.getValue1().toString());
	}

}
