package com.tenpo.api.calculate.service.impl;

import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.external.service.ExternalPercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CalculateServiceImplTest {

	private ExternalPercentageService externalPercentageService;
	private CalculateServiceImpl calculateService;

	@BeforeEach
	void setUp() {
		externalPercentageService = mock(ExternalPercentageService.class);
		calculateService = new CalculateServiceImpl(externalPercentageService);
	}

	@Test
	void testCalculate_validInputs_returnsCorrectResult() {
		BigDecimal num1 = new BigDecimal("100");
		BigDecimal num2 = new BigDecimal("50");
		BigDecimal expectedPercentage = new BigDecimal("10");

		when(externalPercentageService.getPercentage()).thenReturn(expectedPercentage);

		CalculateDtoResponse response = calculateService.calculate(num1, num2);

		BigDecimal expectedSum = new BigDecimal("150");
		BigDecimal expectedTotal =
		 expectedSum.add(expectedSum.multiply(new BigDecimal("0.10"))).setScale(4, BigDecimal.ROUND_HALF_UP);

		assertEquals(expectedTotal, response.result());

		verify(externalPercentageService, times(1)).getPercentage();
	}
}
