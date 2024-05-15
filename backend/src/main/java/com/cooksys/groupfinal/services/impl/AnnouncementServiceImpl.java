package com.cooksys.groupfinal.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.cooksys.groupfinal.dtos.AnnouncementReqDto;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.AnnouncementMapper;
import com.cooksys.groupfinal.mappers.BasicUserMapper;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
	private final BasicUserMapper userMapper;
	private final AnnouncementMapper announcementMapper;

	
	private Company findCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new NotFoundException("A company with the provided id does not exist.");
        }
        return company.get();
    }
	
	public AnnouncementDto createAnnouncement(long companyId, AnnouncementRequestDto announcementRequestDto) {
		if(announcementRequestDto.getTitle()==null||announcementRequestDto.getMessage()==null||announcementRequestDto.getAuthor()==null)
			throw new BadRequestException("The announcement information provided is incomplete");
		Announcement newAnnouncement = announcementMapper.dtoToEntity(announcementRequestDto);
		
		Company company = findCompany(companyId);
		company.getAnnouncements().add(newAnnouncement);
		companyRepository.saveAndFlush(company);
		
		
		newAnnouncement.setCompany(company);
		newAnnouncement.setDate(Timestamp.valueOf(LocalDateTime.now()));
		newAnnouncement.setAuthor(userMapper.dtoToentity(announcementRequestDto.getAuthor()));
		
		announcementRepository.saveAndFlush(newAnnouncement);
		return announcementMapper.entityToDto(newAnnouncement);
	}
	
	public AnnouncementDto getAnnouncement(long announcementId) {
	      Optional<Announcement> announcement = announcementRepository.findByIdAndDeletedFalse(announcementId);
	        if (announcement.isEmpty()) {
	            throw new NotFoundException("An announcement with the provided id does not exist.");
	        }
	        return announcementMapper.entityToDto(announcement.get());
	}
	public AnnouncementDto updateAnnouncement(long announcementId, AnnouncementRequestDto announcementRequestDto) {
		if(announcementRequestDto==null)
			throw new BadRequestException("Updated announcement information not found");
		
		Announcement updatedAnnouncement = announcementMapper.dtoToEntity(announcementRequestDto);
		Announcement currentAnnouncement=announcementMapper.dtoToEntity(getAnnouncement(announcementId));
		
		if(updatedAnnouncement.getTitle()!=null)
			currentAnnouncement.setTitle(updatedAnnouncement.getTitle());
		if(updatedAnnouncement.getMessage()!=null)
			currentAnnouncement.setMessage(updatedAnnouncement.getMessage());
		if(updatedAnnouncement.getAuthor()!=null)
			currentAnnouncement.setAuthor(updatedAnnouncement.getAuthor());
//		
//		Company company = findCompany(companyId);
//		company.getAnnouncements().add(newAnnouncement);
//		companyRepository.saveAndFlush(company);
//		Company company = currentAnnouncement.getCompany();
//		companyRepository.saveAndFlush(company);
		announcementRepository.saveAndFlush(currentAnnouncement);
		
		return announcementMapper.entityToDto(currentAnnouncement);
	}
  
  
	public AnnouncementDto deleteAnnouncement(long announcementId) {
		Announcement announcementToDelete=announcementMapper.dtoToEntity(getAnnouncement(announcementId));
		announcementToDelete.setDeleted(true);
		announcementRepository.saveAndFlush(announcementToDelete);
		return announcementMapper.entityToDto(announcementToDelete);
	}



    private User findUser(Long userId){
        Optional<User> optionalUser =  userRepository.findByIdAndActiveTrue(userId);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User id: " + userId + "not found");
        }
        return optionalUser.get();
    }
//    private Company findCompany(Long companyId){
//        Optional<Company> optionalCompany =  companyRepository.findById(companyId);
//        if(optionalCompany.isEmpty()){
//            throw new NotFoundException("Company id: " + companyId + "not found");
//        }
//        return optionalCompany.get();
//    }

    @Override
    public AnnouncementDto createAnnouncement(Long companyId, Long userId, AnnouncementReqDto announcementReqDto) {

        if(announcementReqDto == null || announcementReqDto.getTitle() == null || announcementReqDto.getMessage() == null){
            throw new BadRequestException("Both Title and Message are required");
        }

        Announcement newAnnouncement = announcementMapper.announcementReqDtoToEntity(announcementReqDto);

        User announcementAuthor = findUser(userId);
        Company announcementCompany = findCompany(companyId);

        newAnnouncement.setAuthor(announcementAuthor);
        newAnnouncement.setCompany(announcementCompany);

        announcementAuthor.getAnnouncements().add(newAnnouncement);
        announcementCompany.getAnnouncements().add(newAnnouncement);

        userRepository.saveAndFlush(announcementAuthor);
        companyRepository.saveAndFlush(announcementCompany);

        announcementRepository.saveAndFlush(newAnnouncement);

        return announcementMapper.entityToDto(newAnnouncement);
    }
}