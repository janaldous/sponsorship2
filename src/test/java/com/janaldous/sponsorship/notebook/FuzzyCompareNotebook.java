package com.janaldous.sponsorship.notebook;

import java.util.List;
import java.util.Optional;
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
import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.repository.postgres.util.FuzzyComparator;
import com.janaldous.sponsorship.repository.postgres.util.TradingAsExtractor;

import lombok.extern.slf4j.Slf4j;

/**
 * Use FuzzyComparator to set if names fuzzy matches
 * @author janaldous
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class FuzzyCompareNotebook {

	@Autowired
	private CompanySponsorRepository companySponsorRepository;
	
	private FuzzyComparator fuzzyComparator = new FuzzyComparator();

	private TradingAsExtractor tradingAsExtractor = new TradingAsExtractor();
	
	@Test
	@Commit
	public void fuzzyCompareCompanySponsorNames() {
		long count = companySponsorRepository.count();
		int page = 0;
		for (int i = 0; i < count;) {
			Page<CompanySponsor> findAll = companySponsorRepository.findAll(PageRequest.of(page, 500));
			
			List<CompanySponsor> fuzzyComparedCompanyNames = findAll.get()
					.map(cs -> {
						CompanyHouseEntry che = cs.getCompanyHouseEntry();
						PDFSponsor pdfSponsor = cs.getPdfSponsor();
						
						if (che != null) {
							String name1 = tradingAsExtractor.extractTradingAs(che.getNormalizedName());
							String name2 = tradingAsExtractor.extractTradingAs(pdfSponsor.getNormalizedName());
							cs.setFuzzyMatches(fuzzyComparator.isSame(name1, Optional.ofNullable(name2).orElse(pdfSponsor.getCompanyName())));
						}
						
						return cs;
					})
					.collect(Collectors.toList());
			
			companySponsorRepository.saveAll(fuzzyComparedCompanyNames);
			
			i += findAll.getSize();
			page++;
			log.info("page " + page + " of " + findAll.getTotalPages());
			log.info("now at company " + i + " of " + count);
		}
	}
	
}
