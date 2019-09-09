package com.versioncontrol.repositoryaccess.common.exception.handler;

import com.versioncontrol.repositoryaccess.api.data.ErrorDetail;
import com.versioncontrol.repositoryaccess.common.RepoConstants;
import com.versioncontrol.repositoryaccess.common.exception.NotFoundException;
import java.net.MalformedURLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * This class handles the exception globally
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView noHandlerFoundExceptionHandler(NoHandlerFoundException e) {

    ErrorDetail errorInfo = new ErrorDetail();
    errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value() + "");
    errorInfo.setErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
    errorInfo.setErrorLevel(RepoConstants.INFO);
    errorInfo.setErrorType(RepoConstants.FUNCTIONAL);
    errorInfo.setDocumentationUrl(RepoConstants.ERROR_DOC_URL);
    errorInfo.setTips(RepoConstants.ERROR_TIPS_NOT_FOUND);
    ModelAndView model = new ModelAndView();
    model.addObject("error", errorInfo);
    model.setViewName("error");
    return model;

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView resourceNotFoundExceptionHandler(NotFoundException e) {
    ErrorDetail errorInfo = new ErrorDetail();
    errorInfo.setErrorCode(e.getCode());
    errorInfo.setErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
    errorInfo.setErrorLevel(RepoConstants.INFO);
    errorInfo.setErrorType(RepoConstants.FUNCTIONAL);
    errorInfo.setDocumentationUrl(RepoConstants.ERROR_DOC_URL);
    errorInfo.setTips(RepoConstants.ERROR_TIPS_NOT_FOUND);
    ModelAndView model = new ModelAndView();
    model.addObject("error", errorInfo);
    model.setViewName("error");
    return model;
  }


  /**
   * This method handles bad request exception
   *
   * @return errordetails
   */
  @ExceptionHandler(MalformedURLException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView badRequestExceptionHandler(MalformedURLException e) {
    ErrorDetail errorInfo = new ErrorDetail();
    errorInfo.setErrorMessage(e.getMessage());
    errorInfo.setErrorLevel(RepoConstants.INFO);
    errorInfo.setErrorType(RepoConstants.FUNCTIONAL);
    errorInfo.setDocumentationUrl(RepoConstants.ERROR_DOC_URL);
    errorInfo.setTips(RepoConstants.ERROR_TIPS_INVALID_URL);
    ModelAndView model = new ModelAndView();
    model.addObject("error", errorInfo);
    model.setViewName("error");
    return model;
  }

  /**
   * This method handles bad request exception
   *
   * @return errordetails
   */
  @ExceptionHandler(HttpClientErrorException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView badRequestExceptionHandler(HttpClientErrorException e) {
    ErrorDetail errorInfo = new ErrorDetail();
    errorInfo.setErrorCode("400");
    errorInfo.setErrorMessage(e.getMessage());
    errorInfo.setErrorLevel(RepoConstants.INFO);
    errorInfo.setErrorType(RepoConstants.FUNCTIONAL);
    errorInfo.setDocumentationUrl(RepoConstants.ERROR_DOC_URL);
    errorInfo.setTips(RepoConstants.ERROR_TIPS_NOT_FOUND);
    ModelAndView model = new ModelAndView();
    model.addObject("error", errorInfo);
    model.setViewName("error");
    return model;
  }

  /**
   * This method handles internal server error exception
   *
   * @return errordetails
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView exceptionHandler(Exception ex) {
    log.error("Internal server error occurred", ex);
    ErrorDetail errorInfo = new ErrorDetail();
    errorInfo.setErrorCode("500");
    errorInfo.setErrorMessage(ex.getMessage());
    errorInfo.setErrorLevel(RepoConstants.ERROR);
    errorInfo.setErrorType(RepoConstants.TECHNICAL);
    errorInfo.setDocumentationUrl(RepoConstants.ERROR_DOC_URL);
    errorInfo.setTips(RepoConstants.ERROR_TIPS_INTERNAL_SERVER_ERROR);
    ModelAndView model = new ModelAndView();
    model.addObject("error", errorInfo);
    model.setViewName("error");
    return model;
  }


}
