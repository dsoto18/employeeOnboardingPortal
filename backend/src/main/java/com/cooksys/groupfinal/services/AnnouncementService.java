package com.cooksys.groupfinal.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;

import com.cooksys.groupfinal.dtos.AnnouncementReqDto;

public interface AnnouncementService {
  
	AnnouncementDto createAnnouncement(long companyId, AnnouncementRequestDto announcementRequestDto);
  
	AnnouncementDto getAnnouncement(long announcementId);
  
	AnnouncementDto updateAnnouncement(long announcementId, AnnouncementRequestDto announcementRequestDto);
  
	AnnouncementDto deleteAnnouncement(long announcementId);

  AnnouncementDto createAnnouncement(Long companyId, Long userId, AnnouncementReqDto announcementReqDto);
}
