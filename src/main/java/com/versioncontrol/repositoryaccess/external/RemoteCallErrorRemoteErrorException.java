package com.versioncontrol.repositoryaccess.external;

public class RemoteCallErrorRemoteErrorException extends AbstractRemoteCallError {

  private final int statusCode;
  private final String statusText;

  public RemoteCallErrorRemoteErrorException(String error, int statusCode, String statusText) {
    super(error);
    this.statusCode = statusCode;
    this.statusText = statusText;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + " [" + statusCode + ", " + statusText + "]";
  }
}
