package com.tenpo.api.calculate.service;

import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;

import java.math.BigDecimal;

public interface ICalculateService {
	CalculateDtoResponse calculate(BigDecimal num1, BigDecimal num2);
}
