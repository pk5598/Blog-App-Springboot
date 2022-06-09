package com.api.blogapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogapp.payloads.ApiResponse;
import com.api.blogapp.payloads.CategoryDto;
import com.api.blogapp.payloads.UserDto;
import com.api.blogapp.services.CategoryService;

@RestController
@RequestMapping("/api/categories") 
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto categoryDto1=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(categoryDto1,HttpStatus.CREATED);
	
		
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId){
		CategoryDto categoryDto1=this.categoryService.updateCategory(categoryDto, categoryId);
		
		return ResponseEntity.ok(categoryDto1);
	}
	
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer id){
		this.categoryService.deleteCategory(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Sucessfully Deleted",true),
				HttpStatus.OK);
				 
	}
	
	
	//get-get category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		
		
		return ResponseEntity.ok(this.categoryService.getAllCategories());
		
	}
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getUserById(@PathVariable("categoryId") int id){
		
		
		return ResponseEntity.ok(this.categoryService.getCategoryById(id));
		
	}
	
	
	

}
