package com.api.blogapp.services;

import java.util.List;

import com.api.blogapp.payloads.CategoryDto;
import com.api.blogapp.payloads.UserDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//update
	
	CategoryDto updateCategory(CategoryDto userDto,Integer category_id);

	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get 
	CategoryDto getCategoryById(Integer id);
	
	//get all
	List<CategoryDto> getAllCategories();

}
