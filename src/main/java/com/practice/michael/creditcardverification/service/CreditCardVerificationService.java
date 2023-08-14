package com.practice.michael.creditcardverification.service;

import com.practice.michael.creditcardverification.entity.CreditCardVerification;
import com.practice.michael.creditcardverification.event.CreditCardApplicationDetails;
import com.practice.michael.creditcardverification.event.NewCreditCardCreatedEvent;
import com.practice.michael.creditcardverification.event.publishing.CreditCardVerificationStatus;
import com.practice.michael.creditcardverification.event.publishing.NewCreditCardVerifiedEvent;
import com.practice.michael.creditcardverification.event.publishing.VerificationStatus;
import com.practice.michael.creditcardverification.repository.CreditCardVerificationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class CreditCardVerificationService {
  private CreditCardVerificationRepository creditCardVerificationRepository;
  private static final int MINIMUM_INCOME_FOR_APPROVAL = 4000;

  public NewCreditCardVerifiedEvent verifyCreditCardApplication(
      NewCreditCardCreatedEvent newCreditCardCreatedEvent) {

    List<CreditCardApplicationDetails> creditCardApplicationDetails =
        newCreditCardCreatedEvent.getCreditCardApplicationDetails();

    List<CreditCardVerificationStatus> creditCardVerificationStatuses =
        getCreditCardVerificationStatuses(creditCardApplicationDetails);

    saveCreditCardVerificationStatus(creditCardVerificationStatuses);

    return NewCreditCardVerifiedEvent.builder()
        .creditCardVerificationStatuses(creditCardVerificationStatuses)
        .build();
  }

  @NotNull
  private static List<CreditCardVerificationStatus> getCreditCardVerificationStatuses(
      List<CreditCardApplicationDetails> creditCardApplicationDetails) {
    return creditCardApplicationDetails.stream()
        .map(
            applicationDetails -> {
              CreditCardVerificationStatus creditCardVerificationStatus =
                  extractApplicationDetailsIntoVerificationStatusInformation(applicationDetails);
              makeVerificationDecision(applicationDetails, creditCardVerificationStatus);
              return creditCardVerificationStatus;
            })
        .toList();
  }

  @NotNull
  private static CreditCardVerificationStatus
      extractApplicationDetailsIntoVerificationStatusInformation(
          CreditCardApplicationDetails applicationDetails) {
    CreditCardVerificationStatus creditCardVerificationStatus =
        CreditCardVerificationStatus.builder().build();
    BeanUtils.copyProperties(applicationDetails, creditCardVerificationStatus);
    return creditCardVerificationStatus;
  }

  private static void makeVerificationDecision(
      CreditCardApplicationDetails applicationDetails,
      CreditCardVerificationStatus creditCardVerificationStatus) {
    if (applicationDetails.getIncome() > MINIMUM_INCOME_FOR_APPROVAL) {
      creditCardVerificationStatus.setVerificationStatus(VerificationStatus.APPROVED);
    } else {
      creditCardVerificationStatus.setVerificationStatus(VerificationStatus.REJECTED);
    }
  }

  private void saveCreditCardVerificationStatus(
      List<CreditCardVerificationStatus> creditCardVerificationStatuses) {
    List<CreditCardVerification> creditCardVerifications =
        creditCardVerificationStatuses.stream()
            .map(
                CreditCardVerificationService
                    ::extractCreditCardVerificationFromCreditCardVerificationStatusInformation)
            .toList();

    log.info("***** Saving credit card application status *****");
    creditCardVerificationRepository.saveAll(creditCardVerifications);
  }

  @NotNull
  private static CreditCardVerification
      extractCreditCardVerificationFromCreditCardVerificationStatusInformation(
          CreditCardVerificationStatus creditCardVerificationStatus) {
    CreditCardVerification creditCardVerification = new CreditCardVerification();
    BeanUtils.copyProperties(creditCardVerificationStatus, creditCardVerification);
    return creditCardVerification;
  }
}
