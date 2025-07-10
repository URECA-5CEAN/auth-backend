package com.ureca.ocean.jjh.auth.dto;



import com.ureca.ocean.jjh.client.dto.UserDto;
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
