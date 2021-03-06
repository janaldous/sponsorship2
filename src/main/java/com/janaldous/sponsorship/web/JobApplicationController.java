package com.janaldous.sponsorship.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.sponsorship.dto.model.JobApplicationCreateDto;
import com.janaldous.sponsorship.dto.model.JobApplicationDto;
import com.janaldous.sponsorship.service.JobApplicationService;

@RestController
public class JobApplicationController {
	
	@Autowired
	private JobApplicationService jobApplicationService;

	@GetMapping("/jobapplication/{id}")
	public JobApplicationDto getJobApplication(@PathVariable Long id) {
		return jobApplicationService.getApplicationById(id);
	}
	
	@GetMapping("/jobapplication")
	public JobApplicationDto getJobApplicationByFilter(@RequestParam Long companySponsorId) {
		return jobApplicationService.getApplicationByCompanySponsorId(companySponsorId);
	}
	
	@PostMapping("/jobapplication")
	public JobApplicationDto postNewJobApplication(@RequestBody JobApplicationCreateDto jobApplicationCreateRequest) {
		return jobApplicationService.createApplication(jobApplicationCreateRequest);
	}
	
}
