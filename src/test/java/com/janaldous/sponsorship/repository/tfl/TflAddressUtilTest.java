package com.janaldous.sponsorship.repository.tfl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TflAddressUtilTest {

	@Test
	void testGetPostCodeDistrict() {
		String address = "Grange Hill Station,London Underground Ltd.,Manor Rd,Chigwell,Essex,IG7 5QB";
		String postCode = TflAddressUtil.getPostCodeDistrict(address);
		assertEquals("IG7", postCode);
	}

	@Test
	void testGetPostCode() {
		String address = "Grange Hill Station,London Underground Ltd.,Manor Rd,Chigwell,Essex,IG7 5QB";
		String postCode = TflAddressUtil.getPostCode(address);
		assertEquals("IG7 5QB", postCode);
	}
	
	@Test
	void testGetPostCode_invalid() {
		String address = "West Ruislip Station,London Underground Ltd.,Ickenham Rd,West Ruislip,Middlesex";
		assertThrows(IllegalArgumentException.class, () -> TflAddressUtil.getPostCode(address));
	}

	@Test
	void testGetPostCodeDistrict_invalid() {
		String address = "West Ruislip Station,London Underground Ltd.,Ickenham Rd,West Ruislip,Middlesex";
		assertThrows(IllegalArgumentException.class, () -> TflAddressUtil.getPostCodeDistrict(address));
	}

	@Test
	void testIsPostCodeValidDistrict() {
		String postCode = "SW7";
		assertTrue(TflAddressUtil.isValidPostCodeDistrict(postCode));
	}
	
	@Test
	void testIsPostCodeValidDistrict_4digits() {
		String postCode = "SW19";
		assertTrue(TflAddressUtil.isValidPostCodeDistrict(postCode));
	}

}
