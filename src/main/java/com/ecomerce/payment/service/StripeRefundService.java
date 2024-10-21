package com.ecomerce.payment.service;


import com.ecomerce.payment.persistence.model.StripeRefunds;
import com.stripe.exception.StripeException;

import java.util.List;
import java.util.Optional;

public interface StripeRefundService {
    public StripeRefunds findById(String id) throws NullPointerException, StripeException;
    public StripeRefunds save(StripeRefunds stripeRefund)throws NullPointerException, StripeException;
    public StripeRefunds update(String refundId, String reason, double amount)throws NullPointerException, StripeException;
    public void delete(String refundId)throws NullPointerException, StripeException;
    public List<StripeRefunds> findAll()throws NullPointerException, StripeException;

    public StripeRefunds createRefund(String invoiceId, int amount,String reason)throws NullPointerException, StripeException;
}