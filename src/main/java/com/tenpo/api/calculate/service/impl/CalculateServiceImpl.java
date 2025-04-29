package com.tenpo.api.calculate.service.impl;

import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.calculate.exception.CalculateException;
import com.tenpo.api.calculate.service.ICalculateService;
import com.tenpo.api.calculate.util.BigDecimalUtils;
import com.tenpo.api.external.service.ExternalPercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Log4j2
@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements ICalculateService {
	private final ExternalPercentageService externalPercentageService;

	@Override
	public CalculateDtoResponse calculate(BigDecimal num1, BigDecimal num2) {
		log.info("Calculando la suma de {} y {}", num1, num2);
		log.info("Validando los números");
		BigDecimalUtils.validateNumbers(num1, num2);

		log.info("Los números son válidos, procediendo con la suma");
		BigDecimal sum = num1.add(num2);
		log.info("La suma de {} y {} es {}", num1, num2, sum);

		log.info("Obteniendo el porcentaje del servicio externo");
		BigDecimal percentage = externalPercentageService.getPercentage();
		log.info("El porcentaje obtenido es {}", percentage);

		log.info("Aplicando el porcentaje a la suma");
		BigDecimal total = BigDecimalUtils.applyPercentage(sum, percentage);
		log.info("El resultado final aplicando el porcentaje es {}", total);

		log.info("Retornando el resultado de la suma");
		return CalculateDtoResponse.builder().result(total).build();
	}
}
