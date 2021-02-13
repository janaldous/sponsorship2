package com.janaldous.sponsorship.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.CompanyHouseEntry;

@Repository
public interface CompanyHouseEntryRepository extends JpaRepository<CompanyHouseEntry, Long> {

}
