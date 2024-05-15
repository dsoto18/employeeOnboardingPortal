package com.cooksys.groupfinal.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.dtos.TeamRequestDto;

public interface TeamService {
	TeamDto createTeam(long companyId, TeamRequestDto teamRequestDto);
	TeamDto findTeam( long id);
	TeamDto updateTeam(long id, TeamRequestDto teamRequestDto);
	TeamDto deleteTeam(long id);
	TeamDto deleteMemberFromTeam(long teamId, long userId);
	
}
