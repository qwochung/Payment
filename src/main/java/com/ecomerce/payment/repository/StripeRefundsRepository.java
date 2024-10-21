package com.ecomerce.payment.repository;

import com.ecomerce.payment.persistence.model.StripeRefunds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripeRefundsRepository extends JpaRepository<StripeRefunds, String> {
}
