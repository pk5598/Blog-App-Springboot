package com.api.blogapp.payloads;



public class ApiResponse {
	
	private String message;
	private boolean success;

	public ApiResponse(String msg, boolean b) {
		this.message=msg;
		this.success=b;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
	

}
