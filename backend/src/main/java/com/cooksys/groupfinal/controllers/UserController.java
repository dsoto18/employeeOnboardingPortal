package com.cooksys.groupfinal.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.*;

import com.cooksys.groupfinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/login")
	@CrossOrigin(origins="*")
    public FullUserDto login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PostMapping("/{companyId}")
    public BasicUserDto createBasicUser(@PathVariable Long companyId, @RequestBody UserRequestDto userRequestDto){
        return userService.createBasicUser(companyId, userRequestDto);
    }

    @GetMapping
    public Set<BasicUserDto> getUserRegistry(){
        return userService.getUserRegistry();
    }

    @DeleteMapping("/{userEmail}")
    public BasicUserDto deleteBasicUser(@PathVariable String userEmail){
        return userService.deleteBasicUser(userEmail);
    }


	
}
