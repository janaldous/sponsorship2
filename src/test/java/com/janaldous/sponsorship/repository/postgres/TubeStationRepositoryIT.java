package com.janaldous.sponsorship.repository.postgres;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.domain.location.TubeStation;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TubeStationRepositoryIT {

	@Autowired
	private TubeStationRepository tubeStationRepository;
	
	@Test
	void testFindAllByPostCodeDistrict() {
		List<TubeStation> results = tubeStationRepository.findAllByPostCodeDistrict("N1");
		
		assertNotNull(results);
		assertTrue(!results.isEmpty());
	}

}
