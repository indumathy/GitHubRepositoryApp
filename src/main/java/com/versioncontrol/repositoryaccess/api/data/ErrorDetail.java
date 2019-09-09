package com.versioncontrol.repositoryaccess.api.data;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom error class
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorDetail implements Serializable {
	/**
	 * serialization id
	 */
	private static final long serialVersionUID = 2192690884702375483L;
	private String errorCode;
	private String errorMessage;
	private String errorLevel;
	private String errorType;
	private String documentationUrl;
	private String tips;

}