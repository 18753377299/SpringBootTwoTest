package com.example.common.exception.customException;

/**自定义异常*/
public class CustomException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}
	public CustomException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
}
