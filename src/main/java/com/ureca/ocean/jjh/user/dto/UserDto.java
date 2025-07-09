package com.ureca.ocean.jjh.user.dto;

import java.util.HashSet;
import java.util.Map;



import lombok.Builder;
import lombok.Data;

@Data
//@Builder 없다. 
public class UserDto {

	private Long id;
	private String name;
	private String email;
	private String password;
	
	private Map<Integer, String> roles;
}
