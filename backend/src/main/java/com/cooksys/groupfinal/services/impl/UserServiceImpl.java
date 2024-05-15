package com.cooksys.groupfinal.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.dtos.*;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotAuthorizedException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.BasicUserMapper;
import com.cooksys.groupfinal.mappers.CredentialsMapper;
import com.cooksys.groupfinal.mappers.FullUserMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
    private final FullUserMapper fullUserMapper;
	private final CredentialsMapper credentialsMapper;
    private final BasicUserMapper basicUserMapper;
    private final CompanyRepository companyRepository;
	
//	private User findUser(String username) {
//        Optional<User> user = userRepository.findByCredentialsUsernameAndActiveTrue(username);
//        if (user.isEmpty()) {
//            throw new NotFoundException("The username provided does not belong to an active user.");
//        }
//        return user.get();
//    }

    private User findUser(String email) {
        Optional<User> user = userRepository.findByProfileEmailAndActiveTrue(email);
        if (user.isEmpty()) {
            throw new NotFoundException("The email provided does not belong to an active user.");
        }
        return user.get();
    }

    private Company findCompany(Long id) {

        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new NotFoundException("A company with the provided id does not exist.");
        }
        return company.get();
    }

    private void verifyGoodRequest(UserRequestDto userRequestDto){
        if(userRequestDto == null){
            throw new BadRequestException("No request body provided");
        }
        CredentialsDto credentialsDto = userRequestDto.getCredentials();
        ProfileDto profileDto = userRequestDto.getProfile();

        if(credentialsDto == null ||  credentialsDto.getPassword() == null || credentialsDto.getPassword().isEmpty()){
            throw new BadRequestException("Please provide password");
        }

        if(profileDto == null || profileDto.getFirstName() == null || profileDto.getLastName() == null
                || profileDto.getPhone() == null || profileDto.getEmail() == null){
            throw new BadRequestException("Please provide first name, last name, phone and email fields");
        }

        if(profileDto.getFirstName().isEmpty() || profileDto.getLastName().isEmpty()
                || profileDto.getPhone().isEmpty() || profileDto.getEmail().isEmpty()){
            throw new BadRequestException("Make sure first name, last name, phone and email are filled in");
        }

    }
	
	@Override
	public FullUserDto login(UserLoginDto userLoginDto) {
		if (userLoginDto == null || userLoginDto.getEmail() == null || userLoginDto.getPassword() == null) {
            throw new BadRequestException("An email and password are required.");
        }
//        Credentials credentialsToValidate = credentialsMapper.dtoToEntity(credentialsDto);
        User userToValidate = findUser(userLoginDto.getEmail());

        if (!userToValidate.getCredentials().getPassword().equals(userLoginDto.getPassword())) {
            throw new NotAuthorizedException("The provided credentials are invalid.");
        }
        if (userToValidate.getStatus().equals("PENDING")) {
        	userToValidate.setStatus("JOINED");
        	userRepository.saveAndFlush(userToValidate);
        }
        return fullUserMapper.entityToFullUserDto(userToValidate);
	}

    @Override
    public BasicUserDto createBasicUser(Long companyId, UserRequestDto userRequestDto) {

        // verifies if full request received, otherwise throws exception
        verifyGoodRequest(userRequestDto);

        User newBasicUser = basicUserMapper.requestDtoToEntity(userRequestDto);
        newBasicUser.setActive(true);
        Company company = findCompany(companyId);

        newBasicUser.getCompanies().add(company);
        company.getEmployees().add(newBasicUser);

        userRepository.saveAndFlush(newBasicUser);

        companyRepository.saveAndFlush(company);

        return basicUserMapper.entityToBasicUserDto(newBasicUser);
    }

    //gets all basic users for user registry
    @Override
    public Set<BasicUserDto> getUserRegistry() {

        Set<User> users = new HashSet<>(userRepository.findAll());

        return basicUserMapper.entitiesToBasicUserDtos(users);
    }

    @Override
    public BasicUserDto deleteBasicUser(String userEmail) {

        User userToDelete = findUser(userEmail);

        userToDelete.setActive(false);

        return basicUserMapper.entityToBasicUserDto(userRepository.saveAndFlush(userToDelete));
    }
}
