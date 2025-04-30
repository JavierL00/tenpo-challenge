package com.tenpo.api.external.service;

import com.tenpo.api.external.exception.ExternalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

class ExternalPercentageServiceTest {

	private RestTemplate restTemplate;
	private ExternalPercentageService externalService;

	@BeforeEach
	void setUp() {
		restTemplate = mock(RestTemplate.class);
		externalService = new ExternalPercentageService(restTemplate);
	}

	@Test
	void testGetPercentage_validResponse_returnsPercentage() {
		when(restTemplate.getForObject(anyString(), eq(Integer[].class))).thenReturn(new Integer[]{15});

		BigDecimal result = externalService.getPercentage();

		assertEquals(new BigDecimal("15"), result);
		verify(restTemplate, times(1)).getForObject(anyString(), eq(Integer[].class));
	}

	@Test
	void testFetchPercentage_nullResponse_throwsExternalException() {
		when(restTemplate.getForObject(anyString(), eq(Integer.class))).thenReturn(null);

		ExternalException ex = assertThrows(ExternalException.class, () -> externalService.fetchPercentage());
		assertEquals("Respuesta vacía del servicio externo", ex.getMessage());
	}

	@Test
	void testGetPercentage_exceptionThrown_throwsExternalException() {
		when(restTemplate.getForObject(anyString(), eq(Integer[].class))).thenThrow(new RuntimeException(
		 "Connection " + "error"));

		ExternalException ex = assertThrows(ExternalException.class, () -> externalService.getPercentage());
		assertEquals("Connection error", ex.getMessage());
	}
}
