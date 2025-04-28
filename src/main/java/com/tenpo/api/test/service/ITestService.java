package com.tenpo.api.test.service;

import com.tenpo.api.test.dto.response.TestDtoResponse;

import java.util.List;

public interface ITestService {
	List<TestDtoResponse> findAll();
}
