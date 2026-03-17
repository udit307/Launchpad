package com.launchpad.service.impl;

import com.launchpad.dto.request.UserRequestDTO;
import com.launchpad.dto.response.UserResponseDTO;
import com.launchpad.entity.CandidateProfile;
import com.launchpad.entity.RecruiterProfile;
import com.launchpad.entity.Role;
import com.launchpad.entity.User;
import com.launchpad.repository.CandidateProfileRepository;
import com.launchpad.repository.RecruiterProfileRepository;
import com.launchpad.repository.UserRepository;
import com.launchpad.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    private final CandidateProfileRepository candidateProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // later we encode
                .role(request.getRole())
                .isActive(true)
                .build();

        User saved = userRepository.save(user);
        if (saved.getRole() == Role.CANDIDATE) {
            CandidateProfile profile = CandidateProfile.builder()
                    .user(saved)
                    .build();
            candidateProfileRepository.save(profile);
        }

        if (saved.getRole() == Role.RECRUITER) {
            RecruiterProfile profile = RecruiterProfile.builder()
                    .user(saved)
                    .build();
            recruiterProfileRepository.save(profile);
        }

        return UserResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

	@Override
	public User findByMail(String Email) {
	User user=  userRepository.findByEmail(Email).orElseThrow(() -> new RuntimeException("Job not found"));
		return user;
	}
}