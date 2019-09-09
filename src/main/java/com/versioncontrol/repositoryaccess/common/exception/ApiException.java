package com.versioncontrol.repositoryaccess.common.exception;


public class ApiException extends RuntimeException {

	/**
	 * serialization id
	 */
	private static final long serialVersionUID = 690626295739853447L;

	private String code;
	
	public ApiException() {
		super();
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param code
	 * @param message
	 */
	public ApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * Get the code
	 * 
	 * @return String
	 */

	public String getCode() {
		return code;
	}

}
