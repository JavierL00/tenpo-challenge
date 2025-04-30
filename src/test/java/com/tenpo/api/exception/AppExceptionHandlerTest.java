package com.tenpo.api.exception;

import com.tenpo.api.calculate.exception.CalculateException;
import com.tenpo.api.external.exception.ExternalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppExceptionHandlerTest {

	private AppExceptionHandler exceptionHandler;
	private WebRequest webRequest;

	@BeforeEach
	void setUp() {
		exceptionHandler = new AppExceptionHandler();
		webRequest = mock(WebRequest.class);
		when(webRequest.getDescription(false)).thenReturn("uri=/calculate");
	}

	@Test
	void testHandleCalculateException_returnsBadRequest() {
		CalculateException ex = new CalculateException("Invalid input");

		ResponseEntity<Object> response = exceptionHandler.handleCalculateException(ex, webRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertResponseStructure(response, "CalculateException", "Invalid input", "/calculate");
	}

	@Test
	void testHandleExternalException_returnsInternalServerError() {
		ExternalException ex = new ExternalException("Service down");

		ResponseEntity<Object> response = exceptionHandler.handleExternalException(ex, webRequest);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertResponseStructure(response, "ExternalException", "Service down", "/calculate");
	}

	private void assertResponseStructure(ResponseEntity<Object> response, String errorType, String message,
																			 String path) {
		assertTrue(response.getBody() instanceof Map);
		Map<?, ?> body = (Map<?, ?>) response.getBody();

		assertEquals(errorType, body.get("error"));
		assertEquals(message, body.get("message"));
		assertEquals(path, body.get("path"));
		assertEquals(response.getStatusCode().value(), body.get("status"));
		assertNotNull(body.get("timestamp"));
	}
}
