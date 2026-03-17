package com.launchpad.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.launchpad.dto.response.ApplicationResponseDTO;

public interface ApplicationService {

	public ApplicationResponseDTO applyToJob(Long candidateId, Long jobId);

	public List<ApplicationResponseDTO> getApplicationsForRecruiter(Long recruiterId);
	
	ApplicationResponseDTO updateApplicationStatus(Long applicationId, Long recruiterId, String status);
	
	Page<ApplicationResponseDTO> getApplicationsByJob(Long jobId,Long recruiterId, int page, int size);
	
	Page<ApplicationResponseDTO> getApplicationsByCandidate(Long candidateId, int page, int size);

	Page<ApplicationResponseDTO> getMyApplications(int page, int size);
}
