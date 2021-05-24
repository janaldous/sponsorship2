package com.janaldous.sponsorship.domain.core;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * Sponsor from https://uktiersponsors.co.uk/
 * @author janaldous
 */
@Entity
@Data
public class PDFSponsor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "normalized_name")
	private String normalizedName;
	
	private String town;
	
	private String industry;
	
	@Column(name = "main_tier")
	private String mainTier;
	
	@Column(name = "sub_tier")
	private String subTier;
	
	private String url;
	
	@Column(name = "social_url")
	private String socialUrl;
	
	@CreationTimestamp
	@Column(name = "date_added", insertable = false, updatable = false )
	private LocalDateTime dateAdded;
	
	@UpdateTimestamp
	@Column(name = "date_added")
	private LocalDateTime dateUpdated;
	
}
