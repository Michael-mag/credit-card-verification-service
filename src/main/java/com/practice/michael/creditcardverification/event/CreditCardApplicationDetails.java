package com.practice.michael.creditcardverification.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditCardApplicationDetails {
  private String firstName;
  private String lastName;
  private Integer income;
  private String address;
  private String refId;
}
