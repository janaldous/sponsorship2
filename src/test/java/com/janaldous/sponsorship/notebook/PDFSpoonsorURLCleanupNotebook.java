package com.janaldous.sponsorship.notebook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.companyhouseapi.CompanyHouseApiException;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class PDFSpoonsorURLCleanupNotebook {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;

	@Test
	void test() throws CompanyHouseApiException, IOException {
		List<PDFSponsor> sponsors = pdfSponsorRepository.findAll();
		Set<String> hosts = new HashSet<>();
		Set<String> nonUnique = new HashSet<>();
		
		sponsors.forEach(x -> {
			String urlStr = x.getUrl();
			
			if (Strings.isNullOrEmpty(urlStr)) return;
			
			URI uri;
			try {
				uri = new URI(urlStr);
				String hostStr = uri.getHost();
				boolean successfullyAdded = hosts.add(hostStr);
				if (!successfullyAdded) {
					nonUnique.add(hostStr);
				}
			} catch (URISyntaxException e) {
				//log.error(e);
			}
		});
		
		nonUnique.forEach(System.out::println);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/USER/Documents/code/stuff2021/sponsorship/output.txt", true));
	    for (String str: nonUnique) {
	    	writer.append(str + "\n");
	    }
	    
	    writer.close();
	}

}
