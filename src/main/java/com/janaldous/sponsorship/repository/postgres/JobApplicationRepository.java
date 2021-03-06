package com.janaldous.sponsorship.repository.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janaldous.sponsorship.domain.core.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

	@Query("SELECT ja "
			+ "FROM JobApplication ja "
			+ "JOIN CompanySponsor cs "
			+ "ON ja.companySponsor = cs "
			+ "WHERE cs.id = :companySponsorId")
	List<JobApplication> findByCompanySponsor(@Param("companySponsorId") Long companySponsorId);

}
