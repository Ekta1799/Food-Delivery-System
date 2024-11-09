package com.food.delivery.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<?> generalException(GeneralException e) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(error);
	}

}
