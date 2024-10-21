package com.ecomerce.payment.persistence.model;

import com.stripe.model.Invoice;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripeInvoice {

    @Id
    private String id;
    private String customerId;
    private String status;
    private String description;
    private String currency="usd";
//    private List<String> invoiceItemId = new ArrayList<>();
    private double total;
    private String chargeId;
    private String paymentIntent;





    public StripeInvoice(Invoice invoice) {
        this.id = invoice.getId();
        this.currency = invoice.getCurrency();
        this.description = invoice.getDescription();
        this.customerId = invoice.getCustomer();
        this.status = invoice.getStatus();
        this.total = invoice.getTotal();
        this.chargeId = invoice.getCharge();
        this.paymentIntent = invoice.getPaymentIntent();

    }


}
