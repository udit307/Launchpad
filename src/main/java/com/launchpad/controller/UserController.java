package com.launchpad.controller;

import com.launchpad.dto.request.UserRequestDTO;
import com.launchpad.dto.response.UserResponseDTO;
import com.launchpad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	
    private final UserService userService;
    
    @GetMapping("/hi")
    public String getmapped() {
    	return "hiii";
    }
    

//    @PostMapping("/register")
//    public UserResponseDTO register(@RequestBody UserRequestDTO request) {
//        return userService.registerUser(request);
//    }
    
    @GetMapping
    public void getUserProfile(@RequestBody UserRequestDTO userRequestDTO) {
    	//UserResponseDTO userResponseDTO=(UserResponseDTO)userRequestDTO;
    	//return userResponseDTO;
    //now we make return type as void latter i will make it as UserResponseDTO
    }
}