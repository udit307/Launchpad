package com.launchpad.service;

import com.launchpad.dto.request.JobRequestDTO;
import com.launchpad.dto.response.JobResponseDTO;
import org.springframework.data.domain.Page;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO request);

    Page<JobResponseDTO> getAllJobs(int page, int size);

    Page<JobResponseDTO> searchByLocation(String location, int page, int size);

    Page<JobResponseDTO> searchBySkills(String skills, int page, int size);

	JobResponseDTO updateJob(Long jobId, JobRequestDTO request);
}