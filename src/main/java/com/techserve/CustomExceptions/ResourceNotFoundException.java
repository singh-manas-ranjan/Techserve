package com.techserve.CustomExceptions;

public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resourceName, String resourceField, int fieldValue){
		super(String.format("%S Not Found with %S : %d", resourceName, resourceField, fieldValue));
	}
	public ResourceNotFoundException(String resourceName, String resourceField, String fieldValue){
		super(String.format("%S Not Found with %S : %s", resourceName, resourceField, fieldValue));
	}
}
