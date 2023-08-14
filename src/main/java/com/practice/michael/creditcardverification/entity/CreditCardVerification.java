package com.practice.michael.creditcardverification.entity;

import com.practice.michael.creditcardverification.event.publishing.VerificationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardVerification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private Integer income;
  private String address;
  private String refId;

  @Enumerated(EnumType.STRING)
  private VerificationStatus verificationStatus = VerificationStatus.REJECTED;
}
