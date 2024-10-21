package com.ecomerce.payment.repository;
import com.ecomerce.payment.persistence.model.StripeInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StripeInvoiceRepository extends JpaRepository<StripeInvoice, String> {
    @Query("MATCH (n:Invoice{customerId: $customerId}) RETURN n;")
    public List<StripeInvoice> findByCustomerId(String customerId);

}
