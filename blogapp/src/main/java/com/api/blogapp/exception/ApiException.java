package com.api.blogapp.exception;

public class ApiException extends RuntimeException{

	public ApiException() {
		super();
	}
	public ApiException(String message) {
		super(message);
		
	}
}
