package com.tenpo.api.exception;

import com.tenpo.api.calculate.exception.CalculateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(CalculateException.class)
	public ResponseEntity<Object> handleCalculateException(CalculateException ex, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST);
		body.put("error", ex.getClass().getSimpleName());
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));

		log.error("Error encountered: {}", body.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
}
