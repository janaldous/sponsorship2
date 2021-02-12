package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.CompanySponsor;
import com.janaldous.sponsorship.domain.PDFSponsor;

@Repository
public interface CompanySponsorRepository extends JpaRepository<CompanySponsor, Long> {

	List<CompanySponsor> findByPdfSponsor(PDFSponsor pdfSponsor);

}
