package com.amigoscode.fraud.domain.repository;

import com.amigoscode.fraud.domain.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckHistoryRepository
  extends JpaRepository<FraudCheckHistory, Integer> {
}
