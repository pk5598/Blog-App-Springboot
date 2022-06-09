package com.api.blogapp.payloads;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.api.blogapp.entities.Post;
import com.api.blogapp.entities.User;

public class CommentDTo {
	
	private int id;
	
	private String content;
	
	public CommentDTo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	


}
