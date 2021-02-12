package com.janaldous.sponsorship.dto.mapper;

import org.springframework.beans.BeanUtils;

import com.janaldous.sponsorship.domain.PDFSponsor;
import com.janaldous.sponsorship.dto.model.PDFSponsorDto;

public class PDFSponsorMapper {

	public static PDFSponsorDto toPDFSponsorDto(PDFSponsor entity) {
		PDFSponsorDto output = new PDFSponsorDto();
		BeanUtils.copyProperties(entity, output);
		return output;
	}
	
	public static PDFSponsor toPDFSponsorEntity(PDFSponsorDto input) {
		PDFSponsor output = new PDFSponsor();
		BeanUtils.copyProperties(input, output);
		return output;
	}
	
}
