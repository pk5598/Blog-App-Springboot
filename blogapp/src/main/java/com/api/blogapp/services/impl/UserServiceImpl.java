package com.api.blogapp.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.api.blogapp.config.AppConstants;
import com.api.blogapp.entities.Role;
import com.api.blogapp.entities.User;
import com.api.blogapp.exception.ResourceNotFoundException;
import com.api.blogapp.payloads.RoleDto;
import com.api.blogapp.payloads.UserDto;
import com.api.blogapp.repository.RoleRepo;
import com.api.blogapp.repository.UserRepo;
import com.api.blogapp.services.UserService;



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		
		
		return this.userToUserDto(savedUser);
	}
	

	@Override
	public UserDto updateUser(UserDto userDto, Integer user_id) {
		User user=this.userRepo.findById(user_id).orElseThrow(
				()->new ResourceNotFoundException(
						"User","Id",user_id));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=this.userRepo.save(user);
		
		UserDto userDto1=this.userToUserDto(updatedUser);
		return userDto1;
		
	}
	
	

	@Override
	public UserDto getUserById(Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException(
						"User","Id",id));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=this.userRepo.findAll();
		
		List<UserDto> userDtos=users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException(
						"User","Id",userId));
		
		this.userRepo.delete(user);


	}
	
	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto,User.class);
		/*
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		*/
		
		
		return user;

	}
	
	public UserDto userToUserDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
		/*
		userDto.setId(userDto.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());
		*/
		
		return userDto;
	}


	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto,User.class);
		
		//encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		
		
		User newUser=this.userRepo.save(user);
		
		System.out.println(newUser.getRoles().size());
		
		UserDto userDto_return=this.modelMapper.map(newUser, UserDto.class);
		
		Set<RoleDto> roleDtoset=new HashSet<RoleDto>();
		
		for (Role role2 : newUser.getRoles()) {
			
			String namString=role2.getName();
			int id=role2.getId();
			
			RoleDto roleDto=new RoleDto();
			roleDto.setName(namString);
			roleDto.setId(id);
			
			roleDtoset.add(roleDto);
			
		}
		userDto_return.setRolesDtos(roleDtoset);
		
		System.out.println(userDto_return.getRolesDtos().size());
		
		return userDto_return;
	}

}
