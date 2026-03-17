package com.launchpad.entity;

import java.util.List;

import com.launchpad.entity.profile.Education;
import com.launchpad.entity.profile.Experience;
import com.launchpad.entity.profile.Skill;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfile extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

   // private String skills; // Later can normalize
//    private String education;
//    private String experience;
    private String resumeUrl; // Phase 2 (file upload)
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;
}