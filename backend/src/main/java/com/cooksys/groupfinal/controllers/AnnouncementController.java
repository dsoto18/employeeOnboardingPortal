package com.cooksys.groupfinal.controllers;


import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementReqDto;
import com.cooksys.groupfinal.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
	
	private final AnnouncementService announcementService;
	
	@PostMapping("/{companyId}")
	AnnouncementDto createAnnouncement(@PathVariable long companyId, @RequestBody AnnouncementRequestDto announcementRequestDto) {
		return announcementService.createAnnouncement(companyId,announcementRequestDto);
	}
	@GetMapping("/{companyId}/{announcementId}")
	AnnouncementDto getAnnouncement(@PathVariable long announcementId) {
		return announcementService.getAnnouncement(announcementId);
	}
	@PatchMapping("/{companyId}/{announcementId}")
	AnnouncementDto updateAnnouncement(@PathVariable long announcementId, @RequestBody AnnouncementRequestDto announcementRequestDto) {
		return announcementService.updateAnnouncement(announcementId,announcementRequestDto);
	}
	
	@DeleteMapping("/{companyId}/{announcementId}")
	AnnouncementDto deleteAnnouncement(@PathVariable long announcementId) {
		return announcementService.deleteAnnouncement(announcementId);
	}

//   private final AnnouncementService announcementService;

   @PostMapping("/{companyId}/{userId}")
   public AnnouncementDto createAnnouncement(@PathVariable Long companyId, @PathVariable Long userId,
                                             @RequestBody AnnouncementReqDto announcementReqDto) {
       return announcementService.createAnnouncement(companyId, userId, announcementReqDto);
   }
}
