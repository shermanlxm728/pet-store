package com.bestbuy.petstore.webservice.exceptions;

public class SqlConnectionException extends RuntimeException{
	public SqlConnectionException(String message) {
		super(message);
	}
}
