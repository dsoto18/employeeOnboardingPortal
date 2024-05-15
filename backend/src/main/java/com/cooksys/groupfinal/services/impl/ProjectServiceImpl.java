package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.dtos.ProjectRequestDto;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    final private ProjectRepository projectRepository;
    final private TeamRepository teamRepository;
    final private ProjectMapper projectMapper;

    private Team findTeam(Long teamId){
        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        if(optionalTeam.isEmpty()){
            throw new NotFoundException("No team with id: " + teamId);
        }
        return optionalTeam.get();
    }

    private Project findProject(Long projectId){
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if(optionalProject.isEmpty()){
            throw new NotFoundException("No project with id: " + projectId);
        }
        return optionalProject.get();
    }

    @Override
    public ProjectDto createProject(Long teamId, ProjectRequestDto projectRequestDto) {
        if(projectRequestDto == null || projectRequestDto.getName() == null || projectRequestDto.getDescription() == null){
            throw new BadRequestException("Project Name and description required");
        }

        Project newProject = projectMapper.projectRequestDtoToEntity(projectRequestDto);
        Team projectTeam = findTeam(teamId);

        newProject.setTeam(projectTeam);
        newProject.setActive(true);

        projectTeam.getProjects().add(newProject);

        teamRepository.saveAndFlush(projectTeam);
        projectRepository.saveAndFlush(newProject);

        return projectMapper.entityToDto(newProject);
    }

    @Override
    public ProjectDto editProject(Long projectId, ProjectRequestDto projectRequestDto) {
//        if(projectRequestDto == null || projectRequestDto.getName() == null || projectRequestDto.getDescription() == null){
//            throw new BadRequestException("Project Name and description required");
//        }

        Project projectToEdit = findProject(projectId);

        if(projectRequestDto.getName() != null){
            projectToEdit.setName(projectRequestDto.getName());
        }
        if(projectRequestDto.getDescription() != null){
            projectToEdit.setDescription(projectRequestDto.getDescription());
        }
        if(projectRequestDto.isActive() != projectToEdit.isActive()){
            projectToEdit.setActive(projectRequestDto.isActive());
        }

        projectRepository.saveAndFlush(projectToEdit);

        return projectMapper.entityToDto(projectToEdit);
    }

    //permanently delete a project
    @Override
    public ProjectDto deleteProject(Long projectId) {

        Project projectToDelete = findProject(projectId);

        Team teamToRemoveProjectFrom = projectToDelete.getTeam();

        teamToRemoveProjectFrom.getProjects().remove(projectToDelete);

        teamRepository.saveAndFlush(teamToRemoveProjectFrom);
        projectRepository.delete(projectToDelete);

        return projectMapper.entityToDto(projectToDelete);
    }

    //in case we need
    @Override
    public ProjectDto getSingleProject(Long projectId) {

        return projectMapper.entityToDto(findProject(projectId));
    }
}
