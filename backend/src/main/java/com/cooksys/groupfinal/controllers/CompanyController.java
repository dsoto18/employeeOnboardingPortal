package com.cooksys.groupfinal.controllers;

import java.util.Set;

import com.cooksys.groupfinal.dtos.*;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
	
	private final CompanyService companyService;
	
	@GetMapping("/{id}/users")
    public Set<FullUserDto> getAllUsers(@PathVariable Long id) {
        return companyService.getAllUsers(id);
    }
	
	@GetMapping("/{id}/announcements")
    public Set<AnnouncementDto> getAllAnnouncements(@PathVariable Long id) {
        return companyService.getAllAnnouncements(id);
    }
	
	@GetMapping("/{id}/teams")
    public Set<TeamDto> getAllTeams(@PathVariable Long id) {
        return companyService.getAllTeams(id);
    }
	
	@GetMapping("/{companyId}/teams/{teamId}/projects") 
	public Set<ProjectDto> getAllProjects(@PathVariable Long companyId, @PathVariable Long teamId) {
		return companyService.getAllProjects(companyId, teamId);
	}

    @GetMapping
    public Set<CompanyDto> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable Long id){
        return companyService.getCompany(id);
    }

    @GetMapping("/{companyId}/users/{userId}")
    public FullUserDto getUserFromCompany(@PathVariable Long companyId, @PathVariable Long userId){
        return companyService.getUserFromCompany(companyId, userId);
    }

    @PostMapping()
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto){
        return companyService.createCompany(companyDto);
    }

    @DeleteMapping("/{companyId}")
    public CompanyDto deleteCompany(@PathVariable Long companyId){
        return companyService.deleteCompany(companyId);
    }

    @PostMapping("/{companyId}/users/{userId}")
    public String addUserToCompany(@PathVariable Long companyId, @PathVariable Long userId){
        return companyService.addUserToCompany(companyId, userId);
    }

    @DeleteMapping("/{companyId}/users/{userId}")
    public String removeUserFromCompany(@PathVariable Long companyId, @PathVariable Long userId){
        return companyService.removeUserFromCompany(companyId, userId);
    }

    @PatchMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto){
        return companyService.updateCompany(id, companyDto);
    }

}
