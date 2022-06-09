package com.api.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogapp.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
		Optional<User> findByEmail(String email);
}
