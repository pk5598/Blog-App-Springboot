package com.api.blogapp.services;

import java.util.List;

import com.api.blogapp.entities.Post;
import com.api.blogapp.payloads.PostDto;
import com.api.blogapp.payloads.PostResponse;

public interface PostService {

	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize, 
			String sortBy,String sortDir);
	
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List<PostDto> getAllPostByCat(Integer catId);
	
	
	//get all post by user
	List<PostDto> getAllPostByUser(Integer userId);
	
	//search Post
	
	List<PostDto> searchPosts(String keyword);
	
	
	
}
