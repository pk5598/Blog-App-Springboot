package com.api.blogapp.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {
	
	private int id;
	
	@NotBlank
	@Size(min = 4,message = "Name Must be min of 4 characters")
	private String name;
	
	@Email(message = "Email is not valid")
	private String email;
	
	@NotBlank
	@Size(min = 3,max = 10,message = "Password must be within 3 to 10 charcters")
	private String password;
	
	@NotBlank
	private String about;
	
	private Set<RoleDto> rolesDtos=new HashSet<>();
	
	public UserDto() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Set<RoleDto> getRolesDtos() {
		return rolesDtos;
	}
	public void setRolesDtos(Set<RoleDto> rolesDtos) {
		this.rolesDtos = rolesDtos;
	}
	
	
	
	
	
}
