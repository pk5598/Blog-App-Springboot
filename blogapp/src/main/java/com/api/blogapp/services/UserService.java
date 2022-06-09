package com.api.blogapp.services;

import java.util.List;

import com.api.blogapp.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,Integer user_id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	

}
