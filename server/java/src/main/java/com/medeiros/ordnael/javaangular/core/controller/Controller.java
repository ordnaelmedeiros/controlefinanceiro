package com.medeiros.ordnael.javaangular.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class Controller {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> error(Exception e) {
		ResponseEntity<String> res = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		return res;
	}

}