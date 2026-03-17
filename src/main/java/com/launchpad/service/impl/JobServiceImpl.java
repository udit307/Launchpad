package com.launchpad.service.impl;

import com.launchpad.dto.request.JobRequestDTO;
import com.launchpad.dto.response.JobResponseDTO;
import com.launchpad.entity.Job;
import com.launchpad.entity.User;
import com.launchpad.exception.ResourceNotFoundException;
import com.launchpad.repository.JobRepository;
import com.launchpad.repository.UserRepository;
import com.launchpad.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public JobResponseDTO createJob(JobRequestDTO request) {

        User recruiter = userRepository.findById(request.getRecruiterId())
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .skills(request.getSkills())
                .location(request.getLocation())
                .recruiter(recruiter)
                .build();

        Job saved = jobRepository.save(job);

        return mapToResponse(saved);
    }

    @Override
    public Page<JobResponseDTO> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return jobRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<JobResponseDTO> searchByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository
                .findByLocationContainingIgnoreCase(location, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<JobResponseDTO> searchBySkills(String skills, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository
                .findBySkillsContainingIgnoreCase(skills, pageable)
                .map(this::mapToResponse);
    }
    @Override
    public JobResponseDTO updateJob(Long jobId, JobRequestDTO request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        User recruiter = (User) authentication.getPrincipal();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException("You are not allowed to update this job");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());

        jobRepository.save(job);

        return mapToResponse(job);
    }

    private JobResponseDTO mapToResponse(Job job) {
        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .skills(job.getSkills())
                .location(job.getLocation())
                .recruiterName(job.getRecruiter().getName())
                .build();
    }
}