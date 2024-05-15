package com.cooksys.groupfinal.mappers;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.dtos.AnnouncementReqDto;
import com.cooksys.groupfinal.entities.Announcement;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { BasicUserMapper.class })
public interface AnnouncementMapper {

	AnnouncementDto entityToDto(Announcement announcement);

  Set<AnnouncementDto> entitiesToDtos(Set<Announcement> announcement);
  
  Announcement dtoToEntity(AnnouncementDto announcementDto);
  Announcement dtoToEntity(AnnouncementRequestDto announcementRequestDto);


  Announcement announcementReqDtoToEntity(AnnouncementReqDto announcementReqDto);
    
}
