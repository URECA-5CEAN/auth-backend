package com.ureca.ocean.jjh.client.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;


import lombok.Builder;
import lombok.Data;

@Data
//@Builder 없다. 
public class UserDto {

	private UUID id;
	private String name;
	private String email;
	private String password;
}
