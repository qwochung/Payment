package com.ecomerce.payment.service;

import com.ecomerce.payment.persistence.model.StripeCustomer;
import com.stripe.exception.StripeException;

public interface StripeCustomerService {


    // Customers
    public StripeCustomer createCustomer(StripeCustomer stripeCustomer) throws Exception;
    public StripeCustomer updateCustomer(String customerid , StripeCustomer stripeCustomer) throws Exception, StripeException;
    public void delete(String stripeCustomerId) throws Exception, StripeException;
    public StripeCustomer findCustomerById(String customerId) throws Exception, StripeException;


    //Payment
//    public StripePayment createPaymentIntent (StripePayment payment) throws StripeException;





}
