package com.launchpad.service;

import com.launchpad.dto.request.UserRequestDTO;
import com.launchpad.dto.response.UserResponseDTO;
import com.launchpad.entity.User;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO request);
    
    User findByMail(String Email);
    
}