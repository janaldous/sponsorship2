package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.PDFSponsor;

@Repository
public interface PDFSponsorRepository extends JpaRepository<PDFSponsor, Long> {

	List<PDFSponsor> findAllByIndustry(String industry, Pageable pageable);
	
}
