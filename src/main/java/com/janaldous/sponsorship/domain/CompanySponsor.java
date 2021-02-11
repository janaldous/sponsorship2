package com.janaldous.sponsorship.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CompanySponsor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PDFSponsor pdfSponsor;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CompanyHouseEntry companyHouseEntry;
	
}
