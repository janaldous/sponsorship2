package com.janaldous.sponsorship.notebook;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;
import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.dto.model.TrainStation;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.service.NearbyStopsService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
//@ActiveProfiles("debug-apiclient")
class MapCompanyToTflZoneNotebook {

	@Autowired
	private NearbyStopsService nearbyStopsService;

	@Autowired
	private CompanySponsorRepository companySponsorRepository;

	@Test
	@Commit
	void test() throws CompanyHouseApiException, InterruptedException {
		// for each company sponsor
		// get postcode - find nearest tfl stop
		// get zone of nearest tfl stop
		// set zone in company sponsor
		long count = companySponsorRepository.count();
		int page = 0;

		for (int i = 0; i < count;) {
			Page<CompanySponsor> findAll = companySponsorRepository.findAll(PageRequest.of(page, 40));

			List<CompanySponsor> pageCompanySponsors = findAll.getContent().stream()
					.filter(x -> "london".equalsIgnoreCase(x.getPdfSponsor().getTown()) && x.getPdfSponsor().getIndustry().equalsIgnoreCase("Computer Programming"))
					.peek(x -> log.info(x.getId() + ""))
					.map(companySponsor -> {
						CompanyHouseEntry companyHouseEntry = companySponsor.getCompanyHouseEntry();
						if (companyHouseEntry == null)
							return companySponsor;

						String addressPostCode = companyHouseEntry.getAddressPostCode();
						List<TrainStation> nearbyStops = nearbyStopsService.getNearbyStops(addressPostCode);
						if (!nearbyStops.isEmpty() && !Strings.isBlank(nearbyStops.get(0).getZone())) {
							String[] zones = nearbyStops.get(0).getZone().split("[\\+/]");
							companySponsor.setTflZones(Arrays.stream(zones).mapToInt(Integer::parseInt).toArray());
						}
						return companySponsor;
					})
					.collect(Collectors.toList());

			companySponsorRepository.saveAll(pageCompanySponsors);
			
			System.out.println("page " + page + " of " + findAll.getTotalPages());
			log.info("page " + page + " of " + findAll.getTotalPages());
			log.info("sleeping");
			Thread.sleep(10000);
			log.info("starting");
			
			page++;
			i += findAll.getSize();
		}
	}

}
