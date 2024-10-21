package com.ecomerce.payment.repository;

import com.ecomerce.payment.persistence.model.StripeInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.plaf.PanelUI;
import java.util.List;

public interface StripeInvoiceItemRepository extends JpaRepository<StripeInvoiceItem, String> {
    @Query(value = "select i from StripeInvoiceItem i where i.invoice.id =:invoiceId")
    public List<StripeInvoiceItem> findByInvoiceId(@Param("invoiceId") String invoiceId);
}
