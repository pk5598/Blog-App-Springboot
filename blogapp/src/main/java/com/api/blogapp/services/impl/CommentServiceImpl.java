package com.api.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blogapp.entities.Comment;
import com.api.blogapp.entities.Post;
import com.api.blogapp.exception.ResourceNotFoundException;
import com.api.blogapp.payloads.CommentDTo;
import com.api.blogapp.repository.CommentRepo;
import com.api.blogapp.repository.PostRepo;
import com.api.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo CommentRepo;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public CommentDTo createComment(CommentDTo commentDTo, Integer PostId) {
		
		Post post=this.postRepo.findById(PostId).orElseThrow(
				()->new ResourceNotFoundException("Post", "postId",PostId));
		
		Comment comment=this.modelMapper.map(commentDTo, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment=this.CommentRepo.save(comment);
		
		
		
		return this.modelMapper.map(savedComment, CommentDTo.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.CommentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId",commentId));
		this.CommentRepo.delete(comment);
		
	}

}
