package com.tenpo.api.calculate.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CalculateDtoRequest {
	private BigDecimal num1;
	private BigDecimal num2;
}
