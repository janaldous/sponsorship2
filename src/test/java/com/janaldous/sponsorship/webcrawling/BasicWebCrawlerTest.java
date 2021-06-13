package com.janaldous.sponsorship.webcrawling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BasicWebCrawlerTest {

	BasicWebCrawler basicWebCrawler = new BasicWebCrawler();
	@Test
	void test1() {
		// job is in div
		assertNotNull(basicWebCrawler.getPageLinks("https://www.100shapes.com/"));
	}
	
	@Test
	void test2() {
		assertNotNull(basicWebCrawler.getPageLinks("https://www.zoopla.co.uk/"));
	}
	
	@Test
	void test3() {
		assertNotNull(basicWebCrawler.getPageLinks("https://www.adludio.com/"));
	}
	
	@Test
	void test() {
		assertTrue(BasicWebCrawler.careerLinkName.matcher("job").matches());
	}
	
}
