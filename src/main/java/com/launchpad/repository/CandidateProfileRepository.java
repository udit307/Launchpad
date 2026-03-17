package com.launchpad.repository;

import com.launchpad.entity.CandidateProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {

	//Optional<CandidateProfile> findByCandidateId(Long candidateId);
}