package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.domain.PDFSponsor;
import com.janaldous.sponsorship.repository.postgres.PDFSponsorRepository;

@RestController
public class PDFSponsorController {

	@Autowired
	private PDFSponsorRepository pdfSponsorRepository;
	
	@GetMapping("/pdfsponsor")
	public Page<PDFSponsor> getPDFSponsor(@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		return pdfSponsorRepository.findAll(pageable);
	}
	
}
