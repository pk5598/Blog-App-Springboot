package com.api.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogapp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	

}
