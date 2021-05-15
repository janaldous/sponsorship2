package com.janaldous.sponsorship.repository.postgres.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FuzzyCompareTest {

	FuzzyComparator<String> fuzzyCompare = new FuzzyComparator<>();

	@ParameterizedTest
	@MethodSource("provideArgsForFuzzyMatchesTrue")
	void testFuzzyMatchesTrue(String a, String b) {
		assertTrue(fuzzyCompare.isSame(a, b));
	}
	
	@ParameterizedTest
	@MethodSource("provideArgsForFuzzyMatchesFalse")
	void testFuzzyMatchesFalse(String a, String b) {
		assertFalse(fuzzyCompare.isSame(a, b));
	}

	private static List<Arguments> provideArgsForFuzzyMatchesTrue() {
        return Arrays.asList(
				Arguments.of("WSO2. TELCO LTD", "WSO2 (UK) Ltd"),
				Arguments.of("XERO (UK) LTD", "Xero UK Ltd"),
				Arguments.of("CORE STREAM LTD", "CoreStream Ltd"),
				Arguments.of("CORE STREAM LTD", "CORE STREAM LTD"),
				Arguments.of("CIM LOGIC LTD", "Cimlogic Ltd"),
				Arguments.of("CLOUD STRATEX LTD", "CloudStratex")
        );
    }
	
	private static List<Arguments> provideArgsForFuzzyMatchesFalse() {
        return Arrays.asList(
        		Arguments.of("WORKSHARE TECHNOLOGY HOLDINGS LTD", "Workshare Ltd")
        );
    }

}
