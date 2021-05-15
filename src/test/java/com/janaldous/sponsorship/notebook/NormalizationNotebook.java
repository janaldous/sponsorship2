package com.janaldous.sponsorship.notebook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;
import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.CompanyHouseEntryRepository;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;
import com.janaldous.sponsorship.repository.postgres.util.NameNormalizer;

import lombok.extern.slf4j.Slf4j;

/**
 * Collection of manipulation of data to normalize company names in PDF sponsor and Company House entries
 * @author janaldous
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class NormalizationNotebook {

	@Autowired
	private CompanyHouseEntryRepository companyHouseEntryRepository;
	
	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;

	private NameNormalizer nameNormalizer = new NameNormalizer();

	@Test
	@Commit
	public void normalizeCompanyHouseEntryNames() {
		long count = companyHouseEntryRepository.count();
		int page = 0;
		for (int i = 0; i < count;) {
			Page<CompanyHouseEntry> findAll = companyHouseEntryRepository.findAll(PageRequest.of(page, 500));
			
			List<CompanyHouseEntry> normalizedCompanyHouseEntries = findAll.get()
					.map(che -> {
						che.setNormalizedName(nameNormalizer.normalize(che.getCompanyName()));
						return che;
					})
					.collect(Collectors.toList());
			
			companyHouseEntryRepository.saveAll(normalizedCompanyHouseEntries);
			
			i += findAll.getSize();
			page++;
			log.info("now at company " + i + " of " + count);
		}
	}
	
	@Test
	@Commit
	public void normalizePdfSponsor() {
		long count = pdfSponsorRepository.count();
		int page = 0;

		for (int i = 0; i < count;) {
			Page<PDFSponsor> findAll = pdfSponsorRepository.findAll(PageRequest.of(page, 500));
			
			List<PDFSponsor> normalizedPDFSponsor = findAll.get()
					.map(che -> {
						che.setNormalizedName(nameNormalizer.normalize(che.getCompanyName()));
						return che;
					})
					.collect(Collectors.toList());
			
			pdfSponsorRepository.saveAll(normalizedPDFSponsor);
			
			i += findAll.getSize();
			page++;
			log.info("now at company " + i + " of " + count);
		}
	}

}
