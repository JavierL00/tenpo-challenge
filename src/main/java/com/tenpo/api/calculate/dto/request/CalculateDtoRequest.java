package com.tenpo.api.calculate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CalculateDtoRequest {
	@NotNull(message = "El primer número no puede ser nulo")
	private BigDecimal num1;

	@NotNull(message = "El segundo número no puede ser nulo")
	private BigDecimal num2;
}
