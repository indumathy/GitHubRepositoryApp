package com.versioncontrol.repositoryaccess.external;

public abstract class AbstractRemoteCallError extends RuntimeException {

  protected AbstractRemoteCallError(String message) {
    super(message);
  }
}
