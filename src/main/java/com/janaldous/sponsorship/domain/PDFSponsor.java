package com.janaldous.sponsorship.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Sponsor from https://uktiersponsors.co.uk/
 * @author janaldous
 */
@Entity
@Data
public class PDFSponsor {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String companyName;
	
	private String town;
	
	private String industry;
	
	private String mainTier;
	
	private String subTier;
	
	private Date dateAdded;

}
