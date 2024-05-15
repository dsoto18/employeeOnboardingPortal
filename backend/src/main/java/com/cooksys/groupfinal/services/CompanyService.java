package com.cooksys.groupfinal.services;

import java.util.Set;

import com.cooksys.groupfinal.dtos.*;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompanyService {

	Set<FullUserDto> getAllUsers(Long id);

	Set<AnnouncementDto> getAllAnnouncements(Long id);

	Set<TeamDto> getAllTeams(Long id);

	Set<ProjectDto> getAllProjects(Long companyId, Long teamId);

	Set<CompanyDto> getAllCompanies();

	CompanyDto getCompany(Long id);

	FullUserDto getUserFromCompany(Long companyId, Long userId);

	CompanyDto createCompany(CompanyDto companyDto);

	CompanyDto deleteCompany(Long companyId);

	String addUserToCompany(Long companyId, Long userId);

	String removeUserFromCompany(Long companyId, Long userId);

	CompanyDto updateCompany(Long id, CompanyDto companyDto);

}
