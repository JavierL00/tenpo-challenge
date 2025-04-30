package com.tenpo.api.test.service.impl;

import com.tenpo.api.test.dto.response.TestDtoResponse;
import com.tenpo.api.test.entity.TestEntity;
import com.tenpo.api.test.repository.ITestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestServiceImplTest {

	private ITestRepository testRepository;
	private TestServiceImpl testService;

	@BeforeEach
	void setUp() {
		testRepository = mock(ITestRepository.class);
		testService = new TestServiceImpl(testRepository);
	}

	@Test
	void testFindAll_returnsDtoList() {
		TestEntity entity1 = new TestEntity();
		entity1.setId(1L);
		entity1.setName("Test 1");

		TestEntity entity2 = new TestEntity();
		entity2.setId(2L);
		entity2.setName("Test 2");

		when(testRepository.findAll()).thenReturn(List.of(entity1, entity2));

		List<TestDtoResponse> result = testService.findAll();

		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId());
		assertEquals("Test 1", result.get(0).getName());
		assertEquals(2L, result.get(1).getId());
		assertEquals("Test 2", result.get(1).getName());

		verify(testRepository, times(1)).findAll();
	}

	@Test
	void testFindAll_whenEmpty_returnsEmptyList() {
		when(testRepository.findAll()).thenReturn(List.of());

		List<TestDtoResponse> result = testService.findAll();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(testRepository, times(1)).findAll();
	}
}
