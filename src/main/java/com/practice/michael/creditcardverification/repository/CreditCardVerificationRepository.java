package com.practice.michael.creditcardverification.repository;

import com.practice.michael.creditcardverification.entity.CreditCardVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardVerificationRepository
    extends JpaRepository<CreditCardVerification, Long> {}
