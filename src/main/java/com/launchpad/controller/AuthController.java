package com.launchpad.controller;

import com.launchpad.dto.request.LoginRequest;
import com.launchpad.dto.request.UserRequestDTO;
import com.launchpad.dto.response.LoginResponse;
import com.launchpad.dto.response.UserResponseDTO;
import com.launchpad.entity.User;
import com.launchpad.security.JwtService;
import com.launchpad.service.UserService;

import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
		this.userService = userService;
        
    }
    
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

    	
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
//        System.out.println(request.getEmail()+" "+
//                        request.getPassword());
        
        User user= userService.findByMail(request.getEmail());
        UserResponseDTO usr=UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        String token = jwtService.generateToken(usr);
        
       

        return new LoginResponse(token,usr);
    }
}