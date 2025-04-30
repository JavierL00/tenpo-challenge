package com.tenpo.api.callhistory.repository;

import com.tenpo.api.callhistory.entity.CallHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICallHistoryRepository extends JpaRepository<CallHistoryEntity, Long> {
}
