package com.amigoscode.fraud.controller;

import com.amigoscode.clients.dto.FraudCheckResponse;
import com.amigoscode.fraud.service.FraudCheckService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public record FraudCheckController(
    FraudCheckService fraudCheckService
  ){

  @GetMapping(path = "{customerId}")
  public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId){
    boolean isFradululentCustomer = fraudCheckService.idFraudulentCustomer(customerId);
    log.info("fraud check request for customer {}", customerId);
    return new FraudCheckResponse(isFradululentCustomer);
  }

}
