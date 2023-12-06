package com.ahk.hotel.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Resource not found on Server");

	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);

	}
	

}
