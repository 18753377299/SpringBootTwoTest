package com.example.common.exception.customException;

/*自定义异常*/
public class ApplicationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message) {
		super(message);
	}
	public ApplicationException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
}
