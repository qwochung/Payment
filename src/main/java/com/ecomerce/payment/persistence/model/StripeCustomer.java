package com.ecomerce.payment.persistence.model;

import com.stripe.model.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="customers")
public class StripeCustomer {
    //require
   @Id
    private String id;
    private String name;
    private int balance;
    private String email;
    private String phone;
    private String currency;
    private String payment_method;
    private String default_Payment_method;

    @Transient
    private Map<String, String> invoice_settings = new HashMap<>();



    public StripeCustomer stripeCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.currency = customer.getCurrency();
        return this;


    }

}
