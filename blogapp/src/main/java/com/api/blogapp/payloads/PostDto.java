package com.api.blogapp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import com.api.blogapp.entities.Category;
import com.api.blogapp.entities.Comment;
import com.api.blogapp.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate; 
	
	private CategoryDto category;
	
	private UserDto user;
	
  
	private Set<CommentDTo> comments=new HashSet<CommentDTo>();
	
	public PostDto() {
		super();
	}
	
	

	public Integer getPostId() {
		return postId;
	}



	public void setPostId(Integer postId) {
		this.postId = postId;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}



	public Set<CommentDTo> getComments() {
		return comments;
	}



	public void setComments(Set<CommentDTo> comments) {
		this.comments = comments;
	}
	
	

}
