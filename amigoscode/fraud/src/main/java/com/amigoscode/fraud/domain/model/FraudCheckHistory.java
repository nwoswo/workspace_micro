package com.amigoscode.fraud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FraudCheckHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  private Integer customerId;
  private Boolean isFraudSter;
  private LocalDateTime createAt;

}