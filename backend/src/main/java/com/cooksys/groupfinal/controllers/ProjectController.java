package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.dtos.ProjectRequestDto;
import com.cooksys.groupfinal.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
	
	private final ProjectService projectService;

	@PostMapping("/{teamId}")
	public ProjectDto createProject(@PathVariable Long teamId, @RequestBody ProjectRequestDto projectRequestDto){
		return projectService.createProject(teamId, projectRequestDto);
	}

	@PatchMapping("/{projectId}")
	public ProjectDto editProject(@PathVariable Long projectId, @RequestBody ProjectRequestDto projectRequestDto){
		return projectService.editProject(projectId, projectRequestDto);
	}

	@DeleteMapping("/{projectId}")
	public ProjectDto deleteProject(@PathVariable Long projectId){
		return projectService.deleteProject(projectId);
	}

	@GetMapping("/{projectId}")
	public ProjectDto getSingleProject(@PathVariable Long projectId) {
		return projectService.getSingleProject(projectId);
	}
}
