package com.api.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.api.blogapp.entities.Category;
import com.api.blogapp.entities.Post;
import com.api.blogapp.entities.User;
import com.api.blogapp.exception.ResourceNotFoundException;
import com.api.blogapp.payloads.PostDto;
import com.api.blogapp.payloads.PostResponse;
import com.api.blogapp.repository.CategoryRepo;
import com.api.blogapp.repository.PostRepo;
import com.api.blogapp.repository.UserRepo;
import com.api.blogapp.services.PostService;

//import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId,Integer catId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("user", "userId", userId));
		Category category=this.categoryRepo.findById(catId).orElseThrow(
				()->new ResourceNotFoundException("Category", "Category id", catId));
		
		
		
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post upatedPost=this.postRepo.save(post);
		
		
		return this.modelMapper.map(upatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		org.springframework.data.domain.Pageable p=PageRequest.of(
				pageNumber, pageSize,
				sort);
		
		Page<Post> postPage=this.postRepo.findAll(p);
		List<Post> allPosts=postPage.getContent();
		
		
		List<PostDto> postDto=allPosts.stream().map(
				(post)->this.modelMapper.map(post, PostDto.class)).
		         collect(Collectors.toList());
		
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		
		postResponse.setLastPage(postPage.isLast());
		
		return postResponse;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(

				()->new ResourceNotFoundException("Post", "postId", postId));
		PostDto postDto=this.modelMapper.map(post,PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getAllPostByCat(Integer catId) {
		
		Category category=this.categoryRepo.findById(catId).orElseThrow(
				()->new ResourceNotFoundException("Category", "categoryId", catId));
		List<Post> posts=this.postRepo.findByCategory(category);
		List<PostDto> postdto=posts.stream()
				.map((post)->
				this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postdto;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User", "userId", userId));
		
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDto=posts.stream().map(
				(post)->this.modelMapper.map(post, PostDto.class)).
		         collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts=this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
