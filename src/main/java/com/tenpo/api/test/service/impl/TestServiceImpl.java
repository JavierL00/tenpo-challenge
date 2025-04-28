package com.tenpo.api.test.service.impl;

import com.tenpo.api.test.dto.response.TestDtoResponse;
import com.tenpo.api.test.entity.TestEntity;
import com.tenpo.api.test.repository.ITestRepository;
import com.tenpo.api.test.service.ITestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {
	private final ITestRepository testRepository;

	@Override
	public List<TestDtoResponse> findAll() {
		log.info("Obteniendo todos los objetos");
		List<TestEntity> entities = testRepository.findAll();

		if (entities.isEmpty()) {
			log.info("No hay objetos para retornar");
			return List.of();
		}

		log.info("Retornando objetos");
		return entities.stream().map(entity -> {
			TestDtoResponse dto = new TestDtoResponse();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).toList();
	}
}
