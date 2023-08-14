package com.practice.michael.creditcardverification.event;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewCreditCardCreatedEvent {
  private EventType eventType = EventType.NEW_CREDIT_CARD;
  private List<CreditCardApplicationDetails> creditCardApplicationDetails;
}
