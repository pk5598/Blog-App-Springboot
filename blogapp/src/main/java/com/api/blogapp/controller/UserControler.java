package com.api.blogapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogapp.payloads.ApiResponse;
import com.api.blogapp.payloads.UserDto;
import com.api.blogapp.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControler {
	
	@Autowired
	private UserService userService;
	
	//post-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDto1=this.userService.createUser(userDto);
		return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
	
		
	}
	
	
	//put-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer userId){
		UserDto updatedUserDto=this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updatedUserDto);
	}
	
	//ADMIN
	//delete-delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id){
		this.userService.deleteUser(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Sucessfully Deleted",true),
				HttpStatus.OK);
				 
	}
	
	
	//get-get user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUser(){
		
		
		return ResponseEntity.ok(this.userService.getAllUsers());
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int id){
		
		
		return ResponseEntity.ok(this.userService.getUserById(id));
		
	}
	

}
