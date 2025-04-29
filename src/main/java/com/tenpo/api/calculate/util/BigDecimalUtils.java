package com.tenpo.api.calculate.util;

import com.tenpo.api.calculate.exception.CalculateException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Log4j2
@UtilityClass
public class BigDecimalUtils {

	private static final int DEFAULT_SCALE = 10;
	private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

	public static BigDecimal safeMultiply(BigDecimal a, BigDecimal b) {
		validateNumbers(a, b);

		log.info("Multiplicando {} y {}", a, b);
		return a.multiply(b).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
	}

	public static BigDecimal safeDivide(BigDecimal a, BigDecimal b) {
		validateNumbers(a, b);

		if (b.compareTo(BigDecimal.ZERO) == 0) {
			log.error("No se puede dividir entre cero.");
			throw new CalculateException("No se puede dividir entre cero.");
		}

		log.info("Dividiendo {} entre {}", a, b);
		return a.divide(b, DEFAULT_SCALE, DEFAULT_ROUNDING);
	}

	public static BigDecimal applyPercentage(BigDecimal base, BigDecimal percentage) {
		validateNumbers(base, percentage);

		log.info("Aplicando el porcentaje a la suma: {}", percentage);
		BigDecimal increment = safeMultiply(base, safeDivide(percentage, BigDecimal.valueOf(100)));
		return base.add(increment).setScale(4, DEFAULT_ROUNDING);
	}

	public static void validateNumbers(BigDecimal num1, BigDecimal num2) {
		if (num1 == null || num2 == null) {
			log.error("Los números no pueden ser nulos");
			throw new CalculateException("Los números no pueden ser nulos");
		}

		if (num1.compareTo(BigDecimal.ZERO) < 0 || num2.compareTo(BigDecimal.ZERO) < 0) {
			log.error("Los números no pueden ser negativos");
			throw new CalculateException("Los números no pueden ser negativos");
		}
	}
}
