package com.api.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogapp.payloads.ApiResponse;
import com.api.blogapp.payloads.CommentDTo;
import com.api.blogapp.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommnetController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("post/{postId}/comment")
	public ResponseEntity<CommentDTo> createComment(
			@RequestBody CommentDTo commentDTo,
			@PathVariable Integer postId){
		CommentDTo commentDTo2=this.commentService.createComment(commentDTo, postId);
		
		return new ResponseEntity<CommentDTo>(commentDTo2,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}/comment")
	public ResponseEntity<ApiResponse> deleteMapping(
			@PathVariable Integer commentId){
	
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Commment Deleted Successfully!",true),HttpStatus.OK);
	}
	

}
