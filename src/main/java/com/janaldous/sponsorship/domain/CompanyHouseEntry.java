package com.janaldous.sponsorship.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Source: Company House https://developer-specs.company-information.service.gov.uk/companies-house-public-data-api
 * @author janaldous
 *
 */
@Entity
@Data
@Accessors(chain = true)
public class CompanyHouseEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long companyNumber;
	
	@Column(name = "company_name")
	private String companyName;
	
	private String etag;
	
	@Column(name = "previous_company_names")
	private String previousCompanyNames;
	
	@Column(name = "address_line_1")
	private String addressLine1;
	
	@Column(name = "address_line_2")
	private String addressLine2;
	
	@Column(name = "address_care_of")
	private String addressCareOf;
	
	@Column(name = "address_country")
	private String addressCountry;
	
	@Column(name = "address_locality")
	private String addressLocality;
	
	@Column(name = "address_po_box")
	private String addressPOBox;
	
	@Column(name = "address_post_code")
	private String addressPostCode;
	
	@Column(name = "address_premises")
	private String addressPremises;
	
	@Column(name = "address_region")
	private String addressRegion;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<SIC> sicCodes;
	
}
