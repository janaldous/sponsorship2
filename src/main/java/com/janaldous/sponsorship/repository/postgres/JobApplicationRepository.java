package com.janaldous.sponsorship.repository.postgres;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

	@Query("SELECT ja FROM JobApplication ja JOIN CompanySponsor cs WHERE cs.id = :companySponsorId")
	Optional<JobApplication> findByCompanySponsor(@Param("companySponsorId") Long companySponsorId);

}
