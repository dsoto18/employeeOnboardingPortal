package com.cooksys.groupfinal.dtos;

import java.sql.Timestamp;

import com.cooksys.groupfinal.entities.User;

public class AnnouncementRequestDto {
	        
    private String title;
    
    private String message;
    
    private BasicUserDto author;

	public String getTitle() {
		return title;
	}
	public String getMessage() {
		return message;
	}
	public BasicUserDto getAuthor() {
		return author;
	}
}
