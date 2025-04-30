package com.tenpo.api.calculate.util;

import com.tenpo.api.calculate.exception.CalculateException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BigDecimalUtilsTest {
	@Test
	void testSafeMultiply_validInputs_returnsCorrectResult() {
		BigDecimal a = new BigDecimal("2.5");
		BigDecimal b = new BigDecimal("4");
		BigDecimal result = BigDecimalUtils.safeMultiply(a, b);

		assertEquals(new BigDecimal("10.0000000000"), result);
	}

	@Test
	void testSafeDivide_validInputs_returnsCorrectResult() {
		BigDecimal a = new BigDecimal("10");
		BigDecimal b = new BigDecimal("4");
		BigDecimal result = BigDecimalUtils.safeDivide(a, b);

		assertEquals(new BigDecimal("2.5000000000"), result);
	}

	@Test
	void testSafeDivide_byZero_throwsException() {
		BigDecimal a = new BigDecimal("10");
		BigDecimal b = BigDecimal.ZERO;

		CalculateException ex = assertThrows(CalculateException.class, () -> BigDecimalUtils.safeDivide(a, b));
		assertEquals("No se puede dividir entre cero.", ex.getMessage());
	}

	@Test
	void testApplyPercentage_validInputs_returnsCorrectResult() {
		BigDecimal base = new BigDecimal("100");
		BigDecimal percentage = new BigDecimal("15");

		BigDecimal result = BigDecimalUtils.applyPercentage(base, percentage);
		assertEquals(new BigDecimal("115.0000"), result);
	}

	@Test
	void testValidateNumbers_nullInput_throwsException() {
		CalculateException ex =
		 assertThrows(CalculateException.class, () -> BigDecimalUtils.validateNumbers(null, BigDecimal.ONE));
		assertEquals("Los números no pueden ser nulos", ex.getMessage());
	}

	@Test
	void testValidateNumbers_negativeInput_throwsException() {
		CalculateException ex =
		 assertThrows(CalculateException.class, () -> BigDecimalUtils.validateNumbers(new BigDecimal("-1"),
			BigDecimal.ONE));
		assertEquals("Los números no pueden ser negativos", ex.getMessage());
	}

	@Test
	void testConstructor_isPrivate_andThrowsException() throws Exception {
		var constructor = BigDecimalUtils.class.getDeclaredConstructor();
		constructor.setAccessible(true);

		Exception ex = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertInstanceOf(UnsupportedOperationException.class, ex.getCause());
		assertEquals("This is a utility class and cannot be instantiated", ex.getCause().getMessage());
	}
}
