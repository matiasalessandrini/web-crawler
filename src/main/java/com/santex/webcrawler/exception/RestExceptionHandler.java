package com.santex.webcrawler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	

  @ExceptionHandler(value = {RuntimeException.class})
  protected ResponseEntity<Object> handleExceptionInternal(RuntimeException ex, WebRequest request) {
	  return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause().getMessage(), ex));
  }
  
  @ExceptionHandler(value = {BadRequestException.class})
  protected ResponseEntity<Object> handleExceptionInternal(BadRequestException ex, WebRequest request) {
	  return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }
  
  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
      return new ResponseEntity<>(apiError, apiError.getStatus());
  }
	        
}
