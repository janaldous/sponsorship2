package com.janaldous.sponsorship.dto.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PDFSponsorDto {

	private Long id;
	
	private String companyName;
	
	private String town;
	
	private String industry;
	
	private String mainTier;
	
	private String subTier;
	
	private LocalDateTime dateAdded;
	
}
