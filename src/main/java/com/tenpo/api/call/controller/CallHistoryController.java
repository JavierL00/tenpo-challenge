package com.tenpo.api.call.controller;

import com.tenpo.api.call.entity.CallHistoryEntity;
import com.tenpo.api.call.service.ICallHistoryService;
import com.tenpo.api.constant.PathConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstant.CALL_HISTORY)
@Tag(name = "History", description = "Historial de llamadas realizadas a la API")
public class CallHistoryController {
	private final ICallHistoryService callHistoryService;

	@GetMapping
	@Operation(summary = "Obtener el historial de llamadas", description = "Obtiene el historial de llamadas realizadas " +
	 "a la API", responses = {
	 @ApiResponse(responseCode = "200", description = "Historial de llamadas obtenido correctamente"),})
	public ResponseEntity<Page<CallHistoryEntity>> getAllHistory(Pageable pageable) {
		return ResponseEntity.ok(callHistoryService.getAllHistory(pageable));
	}
}
