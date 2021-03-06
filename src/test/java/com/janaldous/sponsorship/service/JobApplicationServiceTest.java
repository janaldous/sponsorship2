package com.janaldous.sponsorship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.janaldous.sponsorship.domain.core.ApplicationMethod;
import com.janaldous.sponsorship.domain.core.ApplicationStatus;
import com.janaldous.sponsorship.domain.core.CompanySponsor;
import com.janaldous.sponsorship.domain.core.JobApplication;
import com.janaldous.sponsorship.domain.core.TechCompanyCategory;
import com.janaldous.sponsorship.dto.model.JobApplicationEventDto;
import com.janaldous.sponsorship.exception.ResourceNotFoundException;
import com.janaldous.sponsorship.repository.postgres.CompanySponsorRepository;
import com.janaldous.sponsorship.repository.postgres.JobApplicationRepository;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

	@Mock
	private JobApplicationRepository jobApplicationRepository;

	@Mock
	private CompanySponsorRepository companySponsorRepository;

	@InjectMocks
	private JobApplicationService jobApplicationService;

	@Test
	void testCreateJobApplicationInvalidCompanySponsor() {
		JobApplicationEventDto jobApplicationCreateRequest = new JobApplicationEventDto();
		jobApplicationCreateRequest.setApplicationMethod(ApplicationMethod.WEBSITE)
				.setCompanySponsorId(101L)
				.setStatus(ApplicationStatus.APPLIED)
				.setCategories(Arrays.asList(TechCompanyCategory.CLOUD));

		assertThrows(ResourceNotFoundException.class,
				() -> jobApplicationService.handleJobApplicationEvent(jobApplicationCreateRequest));
	}
	
	@Test
	void testCreateJobApplicationMapping() {
		JobApplicationEventDto jobApplicationCreateRequest = new JobApplicationEventDto();
		jobApplicationCreateRequest.setApplicationMethod(ApplicationMethod.WEBSITE)
				.setCompanySponsorId(101L)
				.setStatus(ApplicationStatus.APPLIED)
				.setCategories(Arrays.asList(TechCompanyCategory.CLOUD));
		CompanySponsor companySponsor = Mockito.mock(CompanySponsor.class);
		
		Mockito.when(companySponsorRepository.findById(Mockito.eq(101L))).thenReturn(Optional.of(companySponsor));

		jobApplicationService.handleJobApplicationEvent(jobApplicationCreateRequest);
		
		ArgumentCaptor<JobApplication> argumentCaptor = ArgumentCaptor.forClass(JobApplication.class);
		Mockito.verify(jobApplicationRepository).save(argumentCaptor.capture());
		JobApplication savedJobApplication = argumentCaptor.getValue();
		
		assertEquals(jobApplicationCreateRequest.getApplicationMethod(), savedJobApplication.getApplicationMethod());
		assertEquals(jobApplicationCreateRequest.getEmail(), savedJobApplication.getEmail());
		assertEquals(jobApplicationCreateRequest.getStatus(), savedJobApplication.getStatus());
		assertEquals(jobApplicationCreateRequest.getCategories(), savedJobApplication.getCategories());
		assertEquals(companySponsor, savedJobApplication.getCompanySponsor());
	}

}
