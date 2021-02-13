package com.janaldous.sponsorship.repository.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.domain.core.PDFSponsor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PDFSponsorRepositoryTest {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@Disabled
	@Test
	void testFindByIndustry() {
		List<PDFSponsor> sponsors = pdfSponsorRepository.findAllByIndustry("Computer Programming", PageRequest.of(0, 14));
		
		assertEquals(14, sponsors.size());
	}
	
	@Test
	void testFindByIndustryAndNullFetchStatus() {
		List<PDFSponsor> sponsors = pdfSponsorRepository.findAllByIndustryAndNullFetchStatus("Computer Programming", PageRequest.of(0, 14));
		
		assertEquals(14, sponsors.size());
	}

}
