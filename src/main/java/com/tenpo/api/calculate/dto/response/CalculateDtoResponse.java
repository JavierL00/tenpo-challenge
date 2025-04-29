package com.tenpo.api.calculate.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CalculateDtoResponse {
	private BigDecimal result;
}
