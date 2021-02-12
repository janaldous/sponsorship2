package com.janaldous.sponsorship.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.CompanySponsor;

@Repository
public interface CompanySponsorRepository extends JpaRepository<CompanySponsor, Long> {

}
