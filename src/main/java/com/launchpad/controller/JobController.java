package com.launchpad.controller;

import com.launchpad.dto.request.JobRequestDTO;
import com.launchpad.dto.response.JobResponseDTO;
import com.launchpad.entity.CandidateProfile;
import com.launchpad.entity.Job;
import com.launchpad.entity.profile.Skill;
import com.launchpad.exception.ResourceNotFoundException;
import com.launchpad.repository.CandidateProfileRepository;
import com.launchpad.repository.JobRepository;
import com.launchpad.service.JobService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final CandidateProfileRepository candidateProfile;
    private final JobRepository jobRepository;

    @PreAuthorize("hasRole('RECUITER')")
    @PostMapping
    public JobResponseDTO createJob(@RequestBody JobRequestDTO request) {
        return jobService.createJob(request);
    }
    
    @PreAuthorize("hasRole('RECRUITER')")
    @PutMapping("/jobs/{id}")
    public JobResponseDTO updateJob(
            @PathVariable Long id,
            @RequestBody JobRequestDTO request) {

        return jobService.updateJob(id, request);
    }

    @GetMapping
    public Page<JobResponseDTO> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return jobService.getAllJobs(page, size);
    }

    @GetMapping("/search/location")
    public Page<JobResponseDTO> searchByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return jobService.searchByLocation(location, page, size);
    }

    @GetMapping("/search/skills")
    public Page<JobResponseDTO> searchBySkills(
            @RequestParam String skills,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return jobService.searchBySkills(skills, page, size);
    }
    
    
    
//    @GetMapping("/recommendations/{candidateId}")
//    public ResponseEntity<List<Job>> getRecommendedJobs(@PathVariable Long candidateId) {
//        // 1. Fetch Candidate profile to get skills
//        CandidateProfile profile = candidateProfile.findByCandidateId(candidateId)
//                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
//        
//        List<Skill> userSkills = profile.getSkills(); // e.g., ["Java", "React", "SQL"]
//
//        // 2. Fetch jobs that match any of those skills
//        // In a real app, use a custom JPQL query: 
//        // "SELECT DISTINCT j FROM Job j JOIN j.requiredSkills s WHERE s IN :userSkills"
//        List<Job> recommendedJobs = jobRepository.findJobsByMatchingSkills(userSkills);
//
//        return ResponseEntity.ok(recommendedJobs);
//    }
    
}





