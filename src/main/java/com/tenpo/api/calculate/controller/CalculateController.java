package com.tenpo.api.calculate.controller;

import com.tenpo.api.calculate.dto.request.CalculateDtoRequest;
import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.calculate.service.ICalculateService;
import com.tenpo.api.constant.PathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstant.CALCULATE)
public class CalculateController {
	private final ICalculateService calculateService;

	@GetMapping
	public ResponseEntity<CalculateDtoResponse> calculate(@RequestBody CalculateDtoRequest calculateDtoRequest) {
		return ResponseEntity.ok(calculateService.calculate(calculateDtoRequest.getNum1(), calculateDtoRequest.getNum2()));
	}
}
