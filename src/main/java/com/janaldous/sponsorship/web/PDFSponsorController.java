package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.domain.core.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

@RestController
public class PDFSponsorController {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@GetMapping("/pdfsponsor")
	public Page<PDFSponsor> getPDFSponsor(Pageable pageable) {
		return pdfSponsorRepository.findAll(pageable);
	}
	
}
