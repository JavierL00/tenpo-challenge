package com.tenpo.api.calculate.controller;

import com.tenpo.api.calculate.dto.request.CalculateDtoRequest;
import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.calculate.service.ICalculateService;
import com.tenpo.api.call.dto.CallHistoryDtoRequest;
import com.tenpo.api.call.service.ICallHistoryService;
import com.tenpo.api.constant.PathConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstant.CALCULATE)
@Tag(name = "Calculation", description = "Operaciones de cálculo con porcentaje dinámico")
public class CalculateController {
	private final ICalculateService calculateService;
	private final ICallHistoryService callHistoryService;

	@GetMapping
	@Operation(summary = "Realizar cálculo", description = "Este endpoint realiza un cálculo con un porcentaje dinámico"
	 , responses = {
	 @ApiResponse(responseCode = "200", description = "Cálculo realizado exitosamente")})
	public ResponseEntity<CalculateDtoResponse> calculate(@RequestBody CalculateDtoRequest calculateDtoRequest) {
		CalculateDtoResponse response = calculateService.calculate(calculateDtoRequest.num1(), calculateDtoRequest.num2());

		callHistoryService.saveHistory(new CallHistoryDtoRequest(PathConstant.CALCULATE, calculateDtoRequest.toString(),
		 response.toString(), false));

		return ResponseEntity.ok(response);
	}
}
