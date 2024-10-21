package com.ecomerce.payment.repository;

import com.ecomerce.payment.persistence.model.StripeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StripeCustomerRepository extends JpaRepository<StripeCustomer,String> {
    @Query("MATCH (n:`Stripe-Customer`) WHERE n.id= $customerId RETURN n")
    Optional<StripeCustomer> findCustomerById(@Param("customerId") String customerId);
}
