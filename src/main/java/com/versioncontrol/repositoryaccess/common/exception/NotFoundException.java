package com.versioncontrol.repositoryaccess.common.exception;


public class NotFoundException extends ApiException {

	/**
	 * serialization Id
	 */
	private static final long serialVersionUID = 1932318692523743091L;

	public NotFoundException() {
		super();
	}

	/**
	 * parameterized constructor
	 * 
	 * @param code
	 * @param message
	 */
	public NotFoundException(String code, String message) {
		super(code, message);
	}
}
