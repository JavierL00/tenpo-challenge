package com.tenpo.api.call.service;

import com.tenpo.api.calculate.dto.request.CalculateDtoRequest;
import com.tenpo.api.calculate.dto.response.CalculateDtoResponse;
import com.tenpo.api.call.entity.CallHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICallHistoryService {
	void saveHistory(
	 String endpoint, CalculateDtoRequest requestParams, CalculateDtoResponse responseData, boolean isError);

	Page<CallHistoryEntity> getAllHistory(Pageable pageable);
}
