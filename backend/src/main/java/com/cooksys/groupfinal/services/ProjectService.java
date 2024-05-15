package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.dtos.ProjectRequestDto;

public interface ProjectService {

    ProjectDto createProject(Long teamId, ProjectRequestDto projectRequestDto);

    ProjectDto editProject(Long projectId, ProjectRequestDto projectRequestDto);

    ProjectDto deleteProject(Long projectId);

    ProjectDto getSingleProject(Long projectId);
}
