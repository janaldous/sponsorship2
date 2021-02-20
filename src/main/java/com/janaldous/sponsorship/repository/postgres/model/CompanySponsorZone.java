package com.janaldous.sponsorship.repository.postgres.model;

import com.janaldous.sponsorship.domain.core.CompanySponsor;

import lombok.Getter;

@Getter
public class CompanySponsorZone {

	private CompanySponsor companySponsor;
	private Integer zone;

	public CompanySponsorZone(CompanySponsor companySponsor, Integer zone) {
		this.companySponsor = companySponsor;
		this.zone = zone;
	}

}
