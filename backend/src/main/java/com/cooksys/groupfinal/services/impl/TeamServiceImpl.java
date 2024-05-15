package com.cooksys.groupfinal.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.dtos.TeamRequestDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;
	private final TeamMapper teamMapper;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	
	private Company findCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new NotFoundException("A company with the provided id does not exist.");
        }
        return company.get();
    }
	private User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("A company with the provided id does not exist.");
        }
        return user.get();
    }
	@Override
	public TeamDto createTeam(long companyId,TeamRequestDto teamRequestDto) {
		if(teamRequestDto.getName()==null||teamRequestDto.getDescription()==null||teamRequestDto.getTeammates()==null)
			throw new BadRequestException("the team information provided is incomplete");
		
		Team newTeam=new Team();
		newTeam.setName(teamRequestDto.getName());
		newTeam.setDescription(teamRequestDto.getDescription());
		
		//pull users from basic user dto
		for(BasicUserDto bu:teamRequestDto.getTeammates()) {
			newTeam.getTeammates().add(findUser(bu.getId()));
		}

		//set user to team
		for(User u: newTeam.getTeammates()) {
			u.getTeams().add(newTeam);
		}
		userRepository.saveAllAndFlush(newTeam.getTeammates());
		
		//set company to team
		Company company = findCompany(companyId);
		company.getTeams().add(newTeam);
		newTeam.setCompany(company);
		companyRepository.saveAndFlush(company);
		
		teamRepository.saveAndFlush(newTeam);
		return teamMapper.entityToDto(newTeam);
		
	}
	@Override
	public TeamDto findTeam( long id) {
	      Optional<Team> team = teamRepository.findByIdAndDeletedFalse(id);
	        if (team.isEmpty()) {
	            throw new NotFoundException("A team with the provided id does not exist.");
	        }
	        return teamMapper.entityToDto(team.get());
	}
	@Override
	public TeamDto updateTeam(long id, TeamRequestDto teamRequestDto) {
		if(teamRequestDto==null)
			throw new BadRequestException("Updated team information not found");
		Team updatedTeam = teamMapper.dtoToEntity(teamRequestDto);
		Team currentTeam=teamMapper.dtoToEntity(findTeam(id));
		if(updatedTeam.getName()!=null)
			currentTeam.setName(updatedTeam.getName());
		if(updatedTeam.getDescription()!=null)
			currentTeam.setDescription(updatedTeam.getDescription());
		if(updatedTeam.getTeammates()!=null) {
			for(User u:updatedTeam.getTeammates()) {
				currentTeam.getTeammates().add(u);

			}
		}
//			currentTeam.setTeammates(updatedTeam.getTeammates());

		teamRepository.saveAndFlush(currentTeam);
		return teamMapper.entityToDto(currentTeam);
		
	}
	
	@Override
	public TeamDto deleteTeam(long id) {
		Team teamToDelete=teamMapper.dtoToEntity(findTeam(id));
		teamToDelete.setDeleted(true);
		teamRepository.saveAndFlush(teamToDelete);
		return teamMapper.entityToDto(teamToDelete);
	}
	@Override
	public TeamDto deleteMemberFromTeam(long teamId, long userId) {
		TeamDto teamDto= findTeam(teamId);
		Team teamToSearch=new Team();
		teamToSearch.setId(teamDto.getId());
		teamToSearch.setName(teamDto.getName());
		teamToSearch.setDescription(teamDto.getDescription());
		
		//pull users from basic user dto
		for(BasicUserDto bu:teamDto.getTeammates()) {
			teamToSearch.getTeammates().add(findUser(bu.getId()));
		}

			
		//search through teammates for teammate to delete
		for(User u:teamToSearch.getTeammates()) {
			if(u.getId()==userId) {
				u.getTeams().remove(teamToSearch);
				teamToSearch.getTeammates().remove(u);
				teamRepository.saveAndFlush(teamToSearch);
				userRepository.saveAndFlush(u);
			}
		}
		return teamMapper.entityToDto(teamToSearch);
		
	}

}
