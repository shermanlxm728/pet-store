package com.bestbuy.petstore.webservice.utils;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bestbuy.petstore.webservice.exceptions.BadRequestException;
import com.bestbuy.petstore.webservice.exceptions.ExceptionResponse;
import com.bestbuy.petstore.webservice.exceptions.PetNotFoundException;
import com.bestbuy.petstore.webservice.exceptions.SqlConnectionException;



@ControllerAdvice
@RestController
public class PetExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
				request.getDescription(false), "ERROR", null);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(PetNotFoundException.class)
	public final ResponseEntity<Object> handlePetNotFoundException(PetNotFoundException exception, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
				request.getDescription(false), "ERROR", null);
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				request.getDescription(false), "ERROR", exception.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(SqlConnectionException.class)
	public final ResponseEntity<Object> handleSqlConnectionException(SqlConnectionException exception, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Sql Connection Error",
				request.getDescription(false), "ERROR", exception.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
