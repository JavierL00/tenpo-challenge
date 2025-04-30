package com.tenpo.api.configuration.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.api.call.dto.CallHistoryDtoRequest;
import com.tenpo.api.call.service.ICallHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestLoggingInterceptor implements HandlerInterceptor {

	private final ObjectMapper objectMapper;
	private final ICallHistoryService callHistoryService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			String requestBody = new String(request.getInputStream().readAllBytes());
			String endpoint = request.getRequestURI();
			boolean isError = response.getStatus() >= 400;

			String responseBody = null;
			if (response instanceof CachedBodyHttpServletResponse cachedResponse) {
				responseBody = new String(cachedResponse.getCachedBody());
			}

			callHistoryService.saveHistory(new CallHistoryDtoRequest(endpoint, requestBody,
			 isError ? (ex != null ? ex.getMessage() : "Error") : responseBody, isError));
		}
		catch (IOException e) {
			log.error("No se pudo leer el cuerpo del request o response para historial", e);
		}
	}
}
