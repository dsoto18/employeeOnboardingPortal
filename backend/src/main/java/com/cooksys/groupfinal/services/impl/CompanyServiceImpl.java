package com.cooksys.groupfinal.services.impl;

import java.util.*;

import com.cooksys.groupfinal.dtos.*;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.mappers.*;
import com.cooksys.groupfinal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyRepository companyRepository;
	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final FullUserMapper fullUserMapper;
	private final AnnouncementMapper announcementMapper;
	private final TeamMapper teamMapper;
	private final ProjectMapper projectMapper;
	private final CompanyMapper companyMapper;
	
	private Company findCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new NotFoundException("A company with the provided id does not exist.");
        }
        return company.get();
    }
	
	private Team findTeam(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            throw new NotFoundException("A team with the provided id does not exist.");
        }
        return team.get();
    }
	
	@Override
	public Set<FullUserDto> getAllUsers(Long id) {
		Company company = findCompany(id);
		Set<User> filteredUsers = new HashSet<>();
		company.getEmployees().forEach(filteredUsers::add);
//		filteredUsers.removeIf(user -> !user.isActive());
		return fullUserMapper.entitiesToFullUserDtos(filteredUsers);
	}

	@Override
	public Set<AnnouncementDto> getAllAnnouncements(Long id) {
		Company company = findCompany(id);
		List<Announcement> sortedList = new ArrayList<Announcement>(company.getAnnouncements());
		sortedList.sort(Comparator.comparing(Announcement::getDate).reversed());
		Set<Announcement> sortedSet = new HashSet<Announcement>(sortedList);
		return announcementMapper.entitiesToDtos(sortedSet);
	}

	@Override
	public Set<TeamDto> getAllTeams(Long id) {
		Company company = findCompany(id);
		return teamMapper.entitiesToDtos(company.getTeams());
	}

	@Override
	public Set<ProjectDto> getAllProjects(Long companyId, Long teamId) {
		Company company = findCompany(companyId);
		Team team = findTeam(teamId);
		if (!company.getTeams().contains(team)) {
			throw new NotFoundException("A team with id " + teamId + " does not exist at company with id " + companyId + ".");
		}
		Set<Project> filteredProjects = new HashSet<>();
		team.getProjects().forEach(filteredProjects::add);
//		filteredProjects.removeIf(project -> !project.isActive());
		return projectMapper.entitiesToDtos(filteredProjects);
	}

	@Override
	public Set<CompanyDto> getAllCompanies(){
		List<Company> companiesList = new LinkedList<>(companyRepository.findAll());
		Set<Company> companiesSet = new HashSet<>(companiesList);
		return companyMapper.entitiesToDtos(companiesSet);
	}

	@Override
	public CompanyDto getCompany(Long id){
		return companyMapper.entityToDto(this.findCompany(id));
	}


	@Override
	public FullUserDto getUserFromCompany(Long companyId, Long userId){
		Optional<User> u = userRepository.findById(userId); // or validate credentials (?)
		if(u.isEmpty()){
			throw new NotFoundException("User id " + userId + " is not found.");
		}
		Company c = this.findCompany(companyId);

		// check if relation exists in table
		return fullUserMapper.entityToFullUserDto(u.get());
	}

	@Override
	public CompanyDto createCompany(CompanyDto companyDto) {
		Company c = companyMapper.dtoToEntity(companyDto);
		companyRepository.saveAndFlush(c);
		return companyDto;
	}

	@Override
	public CompanyDto deleteCompany(Long companyId){
		Optional<Company> c = companyRepository.findById(companyId);
		if(c.isEmpty()){
			throw new NotFoundException("Company id " + companyId + " is not found");
		}
		companyRepository.deleteById(companyId);
		return companyMapper.entityToDto(c.get());
	}

	@Override
	public String addUserToCompany(Long companyId, Long userId) {
		Optional<User> u = userRepository.findById(userId); // or validate credentials (?)
		if(u.isEmpty()){
			throw new NotFoundException("User id " + userId + " is not found.");
		}
		Company c = this.findCompany(companyId);
		Set<Company> usersCompanies = u.get().getCompanies();
		Set<User> companyEmployees = c.getEmployees();
		// check if relation already exists **
//		if(usersCompanies.contains(c)){
//			throw new Exception("User is already employee of given company");
//		}
		// Add relation to database table **
		usersCompanies.add(c);
		u.get().setCompanies(usersCompanies);
		userRepository.save(u.get());
		companyEmployees.add(u.get());
		c.setEmployees(companyEmployees);
		companyRepository.save(c);
		return "User added to company!";
	}

	@Override
	public String removeUserFromCompany(Long companyId, Long userId){
		Optional<User> u = userRepository.findById(userId); // or validate credentials (?)
		if(u.isEmpty()){
			throw new NotFoundException("User id " + userId + " is not found.");
		}
		Company c = this.findCompany(companyId);
		// check to make sure relation exists, if not throw error
		Set<User> companyEmployees = c.getEmployees();
		if(!companyEmployees.contains(u.get())){
			throw new BadRequestException("User is not an employee of selected company");
		}
		Set<Company> usersCompanies = u.get().getCompanies();
		if(!usersCompanies.contains(c)){
			throw new BadRequestException("Company is not employer of given user");
		}
		// remove relation from table
		companyEmployees.remove(u.get());
		usersCompanies.remove(c);
		companyRepository.save(c);
		userRepository.save(u.get());
		return "User removed from company.";
	}

	@Override
	public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
		Company c = this.findCompany(id);

		if(companyDto.getName() != null && !companyDto.getName().isEmpty()) {
			c.setName(companyDto.getName());
		}
		if(companyDto.getDescription() != null && !companyDto.getDescription().isEmpty()) {
			c.setDescription(companyDto.getDescription());
		}
		companyRepository.save(c);

		return companyMapper.entityToDto(c);
	}

}
