package com.tenpo.api.test.repository;

import com.tenpo.api.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestRepository extends JpaRepository<TestEntity, Long> {
}
