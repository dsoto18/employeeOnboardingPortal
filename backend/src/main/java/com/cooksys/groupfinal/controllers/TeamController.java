package com.cooksys.groupfinal.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.dtos.TeamRequestDto;
import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
	
	private final TeamService teamService;
	
	@PostMapping("/{companyid}")
	public TeamDto createTeam(@PathVariable long companyid,@RequestBody TeamRequestDto teamRequestDto) {
		return teamService.createTeam(companyid, teamRequestDto);
	}

	@GetMapping("/{companyid}/{teamId}")
	public TeamDto findTeam( @PathVariable long teamId) {
		return teamService.findTeam(teamId);
	}
	@PatchMapping("/{companyid}/{teamId}")
	public TeamDto updateTeam(@PathVariable long teamId, @RequestBody TeamRequestDto teamRequestDto) {
		return teamService.updateTeam(teamId, teamRequestDto);
	}
	@DeleteMapping("/{companyid}/{teamId}")
	public TeamDto DeleteTeam(@PathVariable long teamId) {
		return teamService.deleteTeam(teamId);
	}
	
	@DeleteMapping("/{companyid}/{teamId}/{userId}")
	public TeamDto DeleteMemberFromTeam(@PathVariable long teamId, @PathVariable long userId) {
		return teamService.deleteMemberFromTeam(teamId, userId);
	}
}
