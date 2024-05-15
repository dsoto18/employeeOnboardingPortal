package com.cooksys.groupfinal.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.*;

import java.util.Set;

public interface UserService {

	FullUserDto login(UserLoginDto userLoginDto);

  BasicUserDto createBasicUser(Long companyId, UserRequestDto userRequestDto);

  Set<BasicUserDto> getUserRegistry();

  BasicUserDto deleteBasicUser(String userEmail);
}
