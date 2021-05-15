package com.janaldous.sponsorship.repository.postgres.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TradingAsExtractorTest {
	TradingAsExtractor tradingAsExtractor = new TradingAsExtractor();

	@ParameterizedTest
	@MethodSource("provideStrings")
	void testFuzzyMatchesFalse(String expected, String name) {
		assertEquals(expected, tradingAsExtractor.extractTradingAs(name));
	}

	private static List<Arguments> provideStrings() {
        return Arrays.asList(
				Arguments.of("T4 Communications UK Limited", "T4 Communications UK Limited t/a Rightcheck"),
				Arguments.of("Conversion Works Limited", "Conversion Works Limited t/a ConversionWorks"),
				Arguments.of("Clicksco UK Limited", "Clicksco UK Limited")
        );
    }

}
