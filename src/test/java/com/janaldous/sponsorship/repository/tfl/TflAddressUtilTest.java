package com.janaldous.sponsorship.repository.tfl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TflAddressUtilTest {

	@Test
	void testGetPostCode() {
		String address = "Grange Hill Station,London Underground Ltd.,Manor Rd,Chigwell,Essex,IG7 5QB";
		String postCode = TflAddressUtil.getPostCodeDistrict(address);
		assertEquals("IG7", postCode);
	}
}
