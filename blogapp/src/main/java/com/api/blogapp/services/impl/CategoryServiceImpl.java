package com.api.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blogapp.entities.Category;
import com.api.blogapp.exception.ResourceNotFoundException;
import com.api.blogapp.payloads.CategoryDto;
import com.api.blogapp.repository.CategoryRepo;
import com.api.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.mapper.map(categoryDto, Category.class);
		Category addedcat=this.categoryRepo.save(category);
		return this.mapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer category_id) {
		Category category=this.categoryRepo.findById(category_id).orElseThrow(
				()->new ResourceNotFoundException("Category", "Category id", category_id));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		this.categoryRepo.save(category);
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "Category_id", categoryId)
				);
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Category category=this.categoryRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Category", "Category_id", id)
				);
		
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categoriesList=this.categoryRepo.findAll();
		
		List<CategoryDto> collecteDtos=categoriesList.stream()
										             .map((cat) ->
										             this.mapper.map(cat, CategoryDto.class))
										             .collect(Collectors.toList())	;	
		return collecteDtos;
	}

}
