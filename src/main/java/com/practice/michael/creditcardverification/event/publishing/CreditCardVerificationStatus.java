package com.practice.michael.creditcardverification.event.publishing;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditCardVerificationStatus {
  private String firstName;
  private String lastName;
  private Integer income;
  private String address;
  private String refId;
  private VerificationStatus verificationStatus;
}
