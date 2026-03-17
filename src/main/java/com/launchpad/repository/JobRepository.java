package com.launchpad.repository;

import com.launchpad.entity.Job;
import com.launchpad.entity.profile.Skill;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    Page<Job> findBySkillsContainingIgnoreCase(String skills, Pageable pageable);

	//List<Job> findJobsByMatchingSkills(List<Skill> userSkills);
}