package com.launchpad.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.launchpad.dto.response.ApplicationResponseDTO;
import com.launchpad.dto.response.ApplicationStatusUpdateDTO;
import com.launchpad.service.ApplicationService;
import com.launchpad.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
	
	ApplicationService applicationService;
	
	@PreAuthorize("hasRole('CANDIDATE')")
	@PostMapping("/apply")
	public ApplicationResponseDTO applyToJob(
	        @RequestParam Long candidateId,
	        @RequestParam Long jobId) {

	    return applicationService.applyToJob(candidateId, jobId);
	}
	@PreAuthorize("hasRole('RECUITER')")
	@GetMapping("/recruiter/{recruiterId}")
	public List<ApplicationResponseDTO> getApplicationsForRecruiter(
	        @PathVariable Long recruiterId) {

	    return applicationService.getApplicationsForRecruiter(recruiterId);
	}
	
	@PreAuthorize("hasRole('RECUITER')")
	@PutMapping("/{applicationId}/status")
	public ApplicationResponseDTO updateStatus(
	        @PathVariable Long applicationId,
	        @RequestParam Long recruiterId,
	        @RequestBody ApplicationStatusUpdateDTO dto) {

	    return applicationService.updateApplicationStatus(
	            applicationId,
	            recruiterId,
	            dto.getStatus());
	}
	@GetMapping("/job/{jobId}")
	public Page<ApplicationResponseDTO> getApplicationsForJob(
	        @PathVariable Long jobId,
	        @RequestParam Long recruiterId,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    return applicationService.getApplicationsByJob(jobId, recruiterId, page, size);
	}
	
	@PreAuthorize("hasRole('CANDIDATE')")
	@GetMapping("/candidate/{candidateId}")
	public Page<ApplicationResponseDTO> getApplicationsForCandidate(
	        @PathVariable Long candidateId,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    return applicationService.getApplicationsByCandidate(candidateId, page, size);
	}
	@PreAuthorize("hasRole('CANDIDATE')")
	@GetMapping("/applications/my")
	public Page<ApplicationResponseDTO> getMyApplications(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    return applicationService.getMyApplications(page, size);
	}
	

}
