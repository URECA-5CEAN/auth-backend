package com.ureca.ocean.jjh.config;

import java.util.List;
import java.util.Optional;

import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);			
		
		if(optionalUser.isPresent()) {
			
			User user = optionalUser.get();

			//MyUserDetails 사용			
			UserDetails userDetails = MyUserDetails.builder()
					.username(user.getName())
					.password(user.getPassword())
					.email(user.getEmail())
					.build();	
			return userDetails;
		}
		
		throw new UsernameNotFoundException(email);
		
	}
}
