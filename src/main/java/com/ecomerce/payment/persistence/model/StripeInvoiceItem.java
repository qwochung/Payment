package com.ecomerce.payment.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice_item")
public class StripeInvoiceItem {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="invoice", referencedColumnName = "id")
    private StripeInvoice invoice;
    private String customerId;
    private int amount;



}
