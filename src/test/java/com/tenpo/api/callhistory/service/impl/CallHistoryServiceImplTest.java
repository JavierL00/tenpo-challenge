package com.tenpo.api.callhistory.service.impl;

import com.tenpo.api.callhistory.dto.CallHistoryDtoRequest;
import com.tenpo.api.callhistory.entity.CallHistoryEntity;
import com.tenpo.api.callhistory.repository.ICallHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CallHistoryServiceImplTest {

	private ICallHistoryRepository repository;
	private CallHistoryServiceImpl service;

	@BeforeEach
	void setUp() {
		repository = mock(ICallHistoryRepository.class);
		service = new CallHistoryServiceImpl(repository);
	}

	@Test
	void testSaveHistory_shouldSaveEntity() {
		CallHistoryDtoRequest dto = new CallHistoryDtoRequest(
			"/calculate",
			Map.of("num1", 10, "num2", 20).toString(),
			Map.of("result", 33).toString(),
			false
		);

		service.saveHistory(dto);

		ArgumentCaptor<CallHistoryEntity> captor = ArgumentCaptor.forClass(CallHistoryEntity.class);
		verify(repository, timeout(1000).times(1)).save(captor.capture());

		CallHistoryEntity saved = captor.getValue();
		assertEquals("/calculate", saved.getEndpoint());
		assertTrue(saved.getRequestParams().contains("num1"));
		assertTrue(saved.getResponseData().contains("result"));
		assertFalse(saved.isError());
		assertNotNull(saved.getTimestamp());
	}

	@Test
	void testGetAllHistory_delegatesToRepository() {
		Pageable pageable = Pageable.unpaged();
		Page<CallHistoryEntity> page = new PageImpl<>(List.of(new CallHistoryEntity()));

		when(repository.findAll(pageable)).thenReturn(page);

		Page<CallHistoryEntity> result = service.getAllHistory(pageable);

		assertEquals(1, result.getContent().size());
		verify(repository, times(1)).findAll(pageable);
	}
}
