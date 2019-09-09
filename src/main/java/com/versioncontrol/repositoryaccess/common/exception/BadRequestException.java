package com.versioncontrol.repositoryaccess.common.exception;


public class BadRequestException extends ApiException {

	/**
	 * serialization id
	 */
	private static final long serialVersionUID = 1040725938020972937L;

	public BadRequestException() {
		super();
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param code
	 * @param message
	 */
	public BadRequestException(String code, String message) {
		super(code, message);
	}

}
