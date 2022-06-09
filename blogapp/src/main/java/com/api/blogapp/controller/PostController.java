package com.api.blogapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.blogapp.config.AppConstants;
import com.api.blogapp.entities.Post;
import com.api.blogapp.payloads.ApiResponse;
import com.api.blogapp.payloads.PostDto;
import com.api.blogapp.payloads.PostResponse;
import com.api.blogapp.services.FileService;
import com.api.blogapp.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileServices;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable("userId") Integer userId,
			@PathVariable("categoryId")Integer categoryId){
		
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	//get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(
			@PathVariable Integer userId){
		
		List<PostDto> postDtos=this.postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(
			@PathVariable Integer categoryId){
		List<PostDto> postDtos=this.postService.getAllPostByCat(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
		
		
		PostResponse postResponse=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/{postId}/posts")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDtos=this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDtos,HttpStatus.OK);
		
	}
	
	//delete
	@DeleteMapping("/{postId}/posts")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is Successfully deleted", true);
		
	}
	
	//update
		@PutMapping("/{postId}/posts")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) {
			PostDto updatedpostDto=this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedpostDto,HttpStatus.ACCEPTED);
			
		}
	
		//search
		@GetMapping("/search/{keyword}/posts")
		public ResponseEntity<List<PostDto>> searchPostByTitle(
				@PathVariable("keyword") String keyword){
			
			List<PostDto> postDtos=this.postService.searchPosts(keyword);
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
		
		//-----------------------------------------------------//
		
		//upload image
		
		
		@Value("${project.image}")
		private String path;
		
		
		@PostMapping("/uploadImage/{postId}/posts")
		public ResponseEntity<PostDto> fileUpload(
				@RequestParam("image")MultipartFile image,
				@PathVariable("postId")Integer postId){
			
			PostDto postDto=this.postService.getPostById(postId);
			String fileName=null;
			try {
				
				fileName=this.fileServices.uploadImage(path, image);
				
				postDto.setImageName(fileName);
				PostDto updatePostDto=this.postService.updatePost(postDto, postId);
				
				return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.CREATED);
			
			} catch (IOException e) {
			    e.printStackTrace();
			    return new ResponseEntity<PostDto>(new PostDto(),HttpStatus.BAD_REQUEST);

			}
			

			
		}
		
		//method to serve image
		@GetMapping(value = "/images/{imageName}/posts",produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName,
				HttpServletResponse response) throws IOException {
			
			InputStream resource=this.fileServices.getResource(path, imageName);
		
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			
			
			StreamUtils.copy(resource, response.getOutputStream());
			
		
		}
		
		
		
		
		
		
		
		
		

		
		
}
