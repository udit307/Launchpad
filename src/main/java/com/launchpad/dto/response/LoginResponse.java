package com.launchpad.dto.response;

import com.launchpad.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserResponseDTO user;
}