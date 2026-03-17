package com.launchpad.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.launchpad.dto.response.ApplicationResponseDTO;
import com.launchpad.entity.Application;
import com.launchpad.entity.ApplicationStatus;
import com.launchpad.entity.Job;
import com.launchpad.entity.User;
import com.launchpad.exception.BadRequestException;
import com.launchpad.exception.ResourceNotFoundException;
import com.launchpad.repository.ApplicationRepository;
import com.launchpad.repository.JobRepository;
import com.launchpad.repository.UserRepository;
import com.launchpad.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	
	private final UserRepository userRepository;
	private final JobRepository jobRepository;
	private final ApplicationRepository applicationRepository;

	@Override
	public ApplicationResponseDTO applyToJob(Long candidateId, Long jobId) {

	    User candidate = userRepository.findById(candidateId)
	            .orElseThrow(() -> new RuntimeException("Candidate not found"));

	    Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found"));

	    // Prevent duplicate apply
	    Optional<Application> existingApplication =
	            applicationRepository.findByCandidateIdAndJobId(candidateId, jobId);

	    if (existingApplication.isPresent()) {
	        throw new RuntimeException("You have already applied for this job");
	    }

	    Application application = Application.builder()
	            .candidate(candidate)
	            .job(job)
	            .status(ApplicationStatus.APPLIED)
	            .build();

	    Application saved = applicationRepository.save(application);

	    return mapToResponse(saved);
	}
	
	@Override
	public List<ApplicationResponseDTO> getApplicationsForRecruiter(Long recruiterId) {

	    List<Application> applications =
	            applicationRepository.findByJobRecruiterId(recruiterId);

	    return applications.stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	
	@Override
	public ApplicationResponseDTO updateApplicationStatus(
	        Long applicationId,
	        Long recruiterId,
	        String status) {

	    Application application = applicationRepository.findById(applicationId)
	            .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

	    Job job = application.getJob();

	 
	    if (!job.getRecruiter().getId().equals(recruiterId)) {
	        throw new BadRequestException("You are not authorized to update this application");
	    }

	    ApplicationStatus newStatus;

	    try {
	        newStatus = ApplicationStatus.valueOf(status.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        throw new BadRequestException("Invalid application status");
	    }

	    application.setStatus(newStatus);

	    Application updatedApplication = applicationRepository.save(application);

	    return mapToResponse(updatedApplication);
	}
	
	@Override
	public Page<ApplicationResponseDTO> getApplicationsByJob(
	        Long jobId,
	        Long recruiterId,
	        int page,
	        int size) {

	    Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

	    if (!job.getRecruiter().getId().equals(recruiterId)) {
	        throw new BadRequestException("You are not authorized to view applications for this job");
	    }

	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

	    Page<Application> applications =
	            applicationRepository.findByJobId(jobId, pageable);

	    return applications.map(this::mapToResponse);
	}
	
	@Override
	public Page<ApplicationResponseDTO> getApplicationsByCandidate(Long candidateId, int page, int size) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

	    Page<Application> applications =
	            applicationRepository.findByCandidateId(candidateId, pageable);

	    return applications.map(this::mapToResponse);
	}
	
	@Override
	public Page<ApplicationResponseDTO> getMyApplications(int page, int size) {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    User candidate = (User) authentication.getPrincipal();

	    Pageable pageable =
	            PageRequest.of(page, size, Sort.by("id").descending());

	    Page<Application> applications =
	            applicationRepository.findByCandidateId(candidate.getId(), pageable);

	    return applications.map(this::mapToResponse);
	}
	
	private ApplicationResponseDTO mapToResponse(Application application) {

	    return ApplicationResponseDTO.builder()
	            .applicationId(application.getId())
	            .candidateId(application.getCandidate().getId())
	            .candidateName(application.getCandidate().getName())
	            .jobId(application.getJob().getId())
	            .jobTitle(application.getJob().getTitle())
	            .status(application.getStatus())
	            .build();
	}

}
