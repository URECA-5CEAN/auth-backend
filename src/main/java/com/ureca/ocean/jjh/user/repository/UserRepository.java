package com.ureca.ocean.jjh.user.repository;

import java.util.Optional;

import com.ureca.ocean.jjh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {
		
	Optional<User> findByEmail(String email); 
}
