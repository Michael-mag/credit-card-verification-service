package com.practice.michael.creditcardverification.messaging;

import com.practice.michael.creditcardverification.event.NewCreditCardCreatedEvent;
import com.practice.michael.creditcardverification.event.publishing.NewCreditCardVerifiedEvent;
import com.practice.michael.creditcardverification.service.CreditCardVerificationService;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class CreditCardApplicationProcessor {
  private CreditCardVerificationService creditCardVerificationService;

  @Bean
  public Function<NewCreditCardCreatedEvent, NewCreditCardVerifiedEvent>
      verifyCreditCardApplication() {
    return newCreditCardCreatedEvent -> {
      NewCreditCardVerifiedEvent newCreditCardVerifiedEvent =
          creditCardVerificationService.verifyCreditCardApplication(newCreditCardCreatedEvent);
      log.info(
          "******* Publishing credit card application verification" + " status: {} *******",
          newCreditCardVerifiedEvent.getCreditCardVerificationStatuses().size());

      return (newCreditCardVerifiedEvent.getCreditCardVerificationStatuses().isEmpty())
          ? null
          : newCreditCardVerifiedEvent;
    };
  }
}
