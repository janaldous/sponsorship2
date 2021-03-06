package com.janaldous.sponsorship.domain.core;

import java.time.LocalDateTime;

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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JobApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "company_sponsor_id", referencedColumnName = "id")
	private CompanySponsor companySponsor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "application_method")
	private ApplicationMethod applicationMethod;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ApplicationStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tech_company_type")
	private TechCompanyCategory techCompanyType;
	
	/**
	 * Website used to check and apply
	 */
	@Column(name = "website")
	private String website;
	
	/**
	 * Email used to apply if applied by email
	 */
	@Column(name = "email")
	private String email;
	
}
