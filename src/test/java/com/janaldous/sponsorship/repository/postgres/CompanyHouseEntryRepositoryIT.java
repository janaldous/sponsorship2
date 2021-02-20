package com.janaldous.sponsorship.repository.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CompanyHouseEntryRepositoryIT {

	@Autowired
	private CompanyHouseEntryRepository companyHouseEntryRepository;
	
	@Test
	void testFindAllByTflZone() {
		Page<CompanyHouseEntry> result = companyHouseEntryRepository.findAllByTflZone(1, PageRequest.of(0, 10));
		
		assertEquals(10, result.getSize());
	}

}
