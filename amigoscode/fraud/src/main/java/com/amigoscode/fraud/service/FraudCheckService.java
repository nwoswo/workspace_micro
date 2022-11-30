package com.amigoscode.fraud.service;

import com.amigoscode.fraud.domain.model.FraudCheckHistory;
import com.amigoscode.fraud.domain.repository.FraudCheckHistoryRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckService(
    FraudCheckHistoryRepository repository
    ) {

  public boolean idFraudulentCustomer(Integer customerId) {
    repository.save(
        FraudCheckHistory.builder()
        .customerId(customerId)
        .isFraudSter(false)
        .createAt(LocalDateTime.now())
        .build());
    return false;
  }
}
