package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.exception.ResourceNotFoundException;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class PDFSponsorController {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@GetMapping("/pdfsponsor")
	public Page<PDFSponsor> getPDFSponsor(Pageable pageable) {
		return pdfSponsorRepository.findAll(pageable);
	}
	
	@PostMapping("/pdfsponsor")
	public PDFSponsor patchPDFSponsor(@RequestBody PDFSponsor sponsor) {
		log.info("received sponsor {}", sponsor);
		log.info("url " + sponsor.getCareersUrl());
		
		PDFSponsor pdfSponsor = pdfSponsorRepository.findById(sponsor.getId())
			.orElseThrow(() -> new ResourceNotFoundException("Cannot find pdf sponsor with id: " + sponsor.getId()));
		
		pdfSponsor.setCareersUrl(sponsor.getCareersUrl());
		
		return pdfSponsorRepository.save(pdfSponsor);
	}
	
}
