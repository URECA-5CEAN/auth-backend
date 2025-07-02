package com.example.authbackend.user.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

//import com.mycom.myapp.user.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {

	private int id;
	private String name;;
}
