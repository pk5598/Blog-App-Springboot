package com.api.blogapp.services;

import com.api.blogapp.payloads.CommentDTo;

public interface CommentService {
	
	CommentDTo createComment(CommentDTo commentDTo,Integer PostId);

	void deleteComment(Integer commentId);
}
