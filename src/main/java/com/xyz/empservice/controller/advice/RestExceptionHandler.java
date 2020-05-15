package com.xyz.empservice.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.xyz.empservice.exception.ResourceNotFoundException;
import com.xyz.empservice.response.ErrorResponse;


@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected ResponseEntity<?> handleResourceNotFound(final ResourceNotFoundException ex,
			final HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorMessage(ex.getMessage());
		error.setRequestedURL(request.getRequestURI());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleAnyException(final Exception ex, final HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorMessage(ex.getMessage());
		error.setRequestedURL(request.getRequestURI());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
