package com.janaldous.sponsorship.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class CompanySponsor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pdf_sponsor_id", referencedColumnName = "id")
	private PDFSponsor pdfSponsor;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "company_house_entry_id", referencedColumnName = "id")
	private CompanyHouseEntry companyHouseEntry;
	
	@Column(name = "fetch_data_status")
	@Enumerated(EnumType.STRING)
	private FetchDataStatus fetchDataStatus;
	
}
