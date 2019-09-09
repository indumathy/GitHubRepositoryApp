package com.versioncontrol.repositoryaccess.external.github;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckAvailabilityBadRequestException extends RuntimeException{

  private final String code;

  private final String message;

}
