package com.tenpo.api.callhistory.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.api.calculate.exception.CalculateException;
import com.tenpo.api.callhistory.dto.CallHistoryDtoRequest;
import com.tenpo.api.callhistory.entity.CallHistoryEntity;
import com.tenpo.api.callhistory.repository.ICallHistoryRepository;
import com.tenpo.api.callhistory.service.ICallHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
	public void saveHistory(CallHistoryDtoRequest request) {
		try {
			CallHistoryEntity history = new CallHistoryEntity();
			history.setTimestamp(LocalDateTime.now());
			history.setEndpoint(request.endpoint());
			history.setRequestParams(serializeObject(request.requestParams()));
			history.setResponseData(serializeObject(request.responseData()));
			history.setError(request.isError());

			callHistoryRepository.save(history);
			log.info("Historial guardado correctamente para endpoint: {}", request);
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
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new CalculateException("Serialization error");
		}
	}
}
