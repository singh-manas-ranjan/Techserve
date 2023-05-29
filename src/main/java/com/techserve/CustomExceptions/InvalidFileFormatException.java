package com.techserve.CustomExceptions;

public class InvalidFileFormatException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFileFormatException() {
		super();
	}
	
	public InvalidFileFormatException(String fileName) {
		super(String.format("%s Extension is Not Supported",fileName.substring(fileName.lastIndexOf('.')) ));
	}
	
}
