package com.tenpo.api.test.controller;

import com.tenpo.api.constant.PathConstant;
import com.tenpo.api.test.dto.response.TestDtoResponse;
import com.tenpo.api.test.service.ITestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstant.TEST)
@Tag(name = "Test", description = "API para pruebas de conexión")
public class TestController {
	private final ITestService testService;

	@GetMapping
	@Operation(summary = "Obtener todas las entidades de prueba", description = "Este endpoint devuelve una lista de " +
	 "todas las entidades de prueba almacenadas en la base de datos.", responses = {
	 @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")})
	public ResponseEntity<List<TestDtoResponse>> test() {
		return ResponseEntity.ok(testService.findAll());
	}
}
