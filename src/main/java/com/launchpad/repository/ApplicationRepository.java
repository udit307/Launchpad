package com.launchpad.repository;

import com.launchpad.entity.Application;
import com.launchpad.entity.User;
import com.launchpad.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate(User candidate);

    List<Application> findByJob(Job job);
    
    Optional<Application> findByCandidateIdAndJobId(Long candidateId, Long jobId);

	List<Application> findByJobRecruiterId(Long recruiterId);
	
	Page<Application> findByJobId(Long jobId, Pageable pageable);
	
	Page<Application> findByCandidateId(Long candidateId, Pageable pageable);
}