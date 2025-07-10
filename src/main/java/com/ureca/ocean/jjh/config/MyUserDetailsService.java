package com.ureca.ocean.jjh.config;



import com.ureca.ocean.jjh.client.UserClient;
import com.ureca.ocean.jjh.client.dto.UserDto;

import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

@Slf4j
public class MyUserDetailsService implements UserDetailsService{


	private final UserClient userClient;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserDto userDto = userClient.getUserByEmail(email);

		if(userDto != null) {
			log.info(userDto.getPassword());
			//MyUserDetails 사용			
			UserDetails userDetails = MyUserDetails.builder()
					.username(userDto.getName())
					.password(userDto.getPassword())
					.email(userDto.getEmail())

					.build();	
			return userDetails;
		}
		
		throw new UsernameNotFoundException(email);
		
	}
}
