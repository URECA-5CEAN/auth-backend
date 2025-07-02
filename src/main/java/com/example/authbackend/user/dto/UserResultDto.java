package com.example.authbackend.user.dto;

import java.util.List;

import lombok.Data;

@Data
//@Builder 없다.
public class UserResultDto {
	private String result;
	private UserDto userDto;
	private List<UserDto> userList;
}
