package com.janaldous.sponsorship.repository.postgres.normalizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.janaldous.sponsorship.repository.postgres.util.NameNormalizer;

class NameNormalizerTest {
	
	NameNormalizer nameNormalizer = new NameNormalizer();

	@Test
	void testNormalizeLimitedCompany_shortLtd() {
		assertEquals("100Starlings Ltd", nameNormalizer.normalize("100Starlings Ltd"));
	}
	
	@Test
	void testNormalizeLimitedCompany_shortLtdPeriod() {
		assertEquals("100Starlings Ltd", nameNormalizer.normalize("100Starlings Ltd."));
	}
	
	@Test
	void testNormalizeNonLimitedCompany_longLtd() {
		assertEquals("100Starlings Ltd", nameNormalizer.normalize("100Starlings Limited"));
	}
	
	@Test
	void testNormalizeNonLimitedCompany_longLtdUppercase() {
		assertEquals("100STARLINGS LTD", nameNormalizer.normalize("100STARLINGS LIMITED"));
	}
	
	@Test
	void testTrim() {
		assertEquals("100Starlings Ltd", nameNormalizer.normalize(" 100Starlings Limited "));
	}
	
	@Test
	void testTrim2() {
		assertEquals("Codeweavers Ltd", nameNormalizer.normalize("Codeweavers Ltd."));
	}

	
	
}
