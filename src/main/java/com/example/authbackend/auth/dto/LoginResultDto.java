package com.example.authbackend.auth.dto;



import com.example.authbackend.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDto {
	private String result;
	private String token;
	private UserDto userDto;
}
