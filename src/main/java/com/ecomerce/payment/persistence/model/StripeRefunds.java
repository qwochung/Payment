
package com.ecomerce.payment.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="refunds")
public class StripeRefunds {
    @Id
    private String id;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "invoice", referencedColumnName = "id")
    private StripeInvoice invoice;
    private String paymentId;
    private String reason;
    private String status;


}