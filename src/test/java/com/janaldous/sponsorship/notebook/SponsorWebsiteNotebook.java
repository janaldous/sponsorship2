package com.janaldous.sponsorship.notebook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.config.UkTierSponsorApiConfig;
import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;
import com.janaldous.sponsorship.repository.uktiersponsor.UKTierSponsorApi;
import com.janaldous.sponsorship.repository.uktiersponsor.UKTierSponsorApiException;
import com.janaldous.uktiersponsors.dto.Company;
import com.janaldous.uktiersponsors.dto.CompanySearchResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Fetch sponsor website and save to pdfsponsor
 * @author janaldous
 *
 */
@ExtendWith(SpringExtension.class)
@Import({UkTierSponsorApiConfig.class, UKTierSponsorApi.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
@ActiveProfiles("debug-apiconfig")
public class SponsorWebsiteNotebook {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@Autowired
	private UKTierSponsorApi ukTierSponsorApi;
	
	@Test
	@Commit
	public void fetchAndSaveWebsite() {
		long count = pdfSponsorRepository.count();
		int page = 1000;
		for (int i = page*100; i < count;) {
			Page<PDFSponsor> pageResult = pdfSponsorRepository.findAll(PageRequest.of(page, 100));
			
			List<PDFSponsor> updatedList = pageResult.getContent().parallelStream()
					.filter(x -> x.getUrl() == null && x.getTown().equalsIgnoreCase("london") && x.getIndustry().equalsIgnoreCase("Computer Programming"))
					.peek(x -> log.info(x.getId() + ""))
					.map(ps -> {
						try {
							CompanySearchResponse searchResult = ukTierSponsorApi.searchCompanies(ps.getCompanyName(), ps.getIndustry(), ps.getTown());
							if (!searchResult.getCompanies().isEmpty()) {
								Company firstResult = searchResult.getCompanies().get(0);
								if (firstResult.getWebsite() != null && firstResult.getWebsite().length() < 255) {
									ps.setUrl(firstResult.getWebsite());
								} else {
									log.warn(firstResult.getWebsite() + " is too long");
								}
								if (firstResult.getSocialWebsite() != null 
										&& firstResult.getSocialWebsite().toLowerCase().contains("linkedin")) {
									if (firstResult.getSocialWebsite().length() < 255) {
										ps.setSocialUrl(firstResult.getSocialWebsite());										
									} else {
										log.warn(firstResult.getSocialWebsite() + " is too long");
									}
								}
							}
						} catch (UKTierSponsorApiException e) {
							throw new RuntimeException(e);
						}
						
						return ps;
					})
					.collect(Collectors.toList());
			
			pdfSponsorRepository.saveAll(updatedList);
			
			if (page == 2000) return;
			
			i += pageResult.getSize();
			page++;
			log.info("now at company " + i + " of " + count);
		}
	}
	
}
