package com.api.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogapp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	
	
	
}
