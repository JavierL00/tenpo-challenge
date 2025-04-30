package com.tenpo.api.call.service;

import com.tenpo.api.call.dto.CallHistoryDtoRequest;
import com.tenpo.api.call.entity.CallHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICallHistoryService {
	void saveHistory(CallHistoryDtoRequest request);

	Page<CallHistoryEntity> getAllHistory(Pageable pageable);
}
