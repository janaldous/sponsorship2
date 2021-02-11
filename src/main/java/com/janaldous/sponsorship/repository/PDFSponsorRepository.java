package com.janaldous.sponsorship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.PDFSponsor;

@Repository
public interface PDFSponsorRepository extends JpaRepository<PDFSponsor, Long> {

}
