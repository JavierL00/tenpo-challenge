package com.tenpo.api.call.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.api.calculate.dto.request.CalculateDtoRequest;
import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.calculate.exception.CalculateException;
import com.tenpo.api.call.dto.response.CallHistoryDtoResponse;
import com.tenpo.api.call.entity.CallHistoryEntity;
import com.tenpo.api.call.repository.ICallHistoryRepository;
import com.tenpo.api.call.service.ICallHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class CallHistoryServiceImpl implements ICallHistoryService {
	private final ICallHistoryRepository callHistoryRepository;

	@Async
	public void saveHistory(
	 String endpoint, CalculateDtoRequest requestParams, CalculateDtoResponse responseData,
	 boolean isError
	) {
		try {
			CallHistoryEntity history = new CallHistoryEntity();
			history.setTimestamp(LocalDateTime.now());
			history.setEndpoint(endpoint);
			history.setRequestParams(serializeObject(requestParams));
			history.setResponseData(serializeObject(responseData));
			history.setError(isError);

			callHistoryRepository.save(history);
			log.info("Historial guardado correctamente para endpoint: {}", endpoint);
		}
		catch (Exception e) {
			log.error("Error guardando historial de llamada", e);
		}
	}

	@Override
	public Page<CallHistoryEntity> getAllHistory(Pageable pageable) {
		return callHistoryRepository.findAll(pageable);
	}

	private String serializeObject(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new CalculateException("Serialization error");
		}
	}
}
