package com.tenpo.api.call.repository;

import com.tenpo.api.call.entity.CallHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICallHistoryRepository extends JpaRepository<CallHistoryEntity, Long> {
}
