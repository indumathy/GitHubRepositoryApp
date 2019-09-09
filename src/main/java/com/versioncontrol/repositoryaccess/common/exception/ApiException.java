package com.versioncontrol.repositoryaccess.common.exception;

/*
Base class for custom exception classes
 */
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
