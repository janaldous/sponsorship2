package com.janaldous.sponsorship.repository.postgres.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FuzzyCompareTest {

	FuzzyCompare fuzzyCompare = new FuzzyCompare();

	@ParameterizedTest
	@MethodSource("provideStringsForIsBlankList")
	void testFuzzyMatchesTrue(String a, String b) {
		assertTrue(fuzzyCompare.isSame(a, b));
	}

	private static List<Arguments> provideStringsForIsBlankList() {
        return Arrays.asList(
        		Arguments.of("WORKSHARE TECHNOLOGY HOLDINGS LTD", "Workshare Ltd"),
				Arguments.of("WSO2. TELCO LTD", "WSO2 (UK) Ltd"),
				Arguments.of("XERO (UK) LTD", "Xero UK Ltd"),
				Arguments.of("CORE STREAM LTD", "CoreStream Ltd")
        );
    }

}
