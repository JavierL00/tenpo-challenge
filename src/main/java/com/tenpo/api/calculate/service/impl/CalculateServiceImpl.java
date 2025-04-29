package com.tenpo.api.calculate.service.impl;

import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.calculate.exception.CalculateException;
import com.tenpo.api.calculate.service.ICalculateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Log4j2
@Service
public class CalculateServiceImpl implements ICalculateService {

	@Override
	public CalculateDtoResponse calculate(BigDecimal num1, BigDecimal num2) {
		log.info("Calculando la suma de {} y {}", num1, num2);
		CalculateDtoResponse response = new CalculateDtoResponse();


		log.info("Validando los números");
		validNumbers(num1, num2);

		log.info("La suma de {} y {} es {}", num1, num2, num1.add(num2));
		response.setResult(num1.add(num2));

		log.info("Retornando el resultado de la suma");
		return response;
	}

	private void validNumbers(BigDecimal num1, BigDecimal num2) {
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
