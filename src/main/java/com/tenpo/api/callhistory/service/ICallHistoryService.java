package com.tenpo.api.callhistory.service;

import com.tenpo.api.callhistory.dto.CallHistoryDtoRequest;
import com.tenpo.api.callhistory.entity.CallHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICallHistoryService {
	void saveHistory(CallHistoryDtoRequest request);

	Page<CallHistoryEntity> getAllHistory(Pageable pageable);
}
