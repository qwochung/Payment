package com.ecomerce.payment.service;

import com.ecomerce.payment.persistence.model.StripeInvoice;
import com.stripe.exception.StripeException;

import java.util.List;

public interface StripeInvoiceService {
    public StripeInvoice createInvoice(String customerId) throws StripeException;
    public StripeInvoice save(StripeInvoice invoice) throws StripeException;
    public List<StripeInvoice> findInvoiceByCustomer(String customerId) throws StripeException;
    public StripeInvoice findInvoiceById(String invoiceId) throws StripeException;
    public void delete(String invoiceId) throws StripeException;
    public StripeInvoice finalize(String invoiceId) throws StripeException;
    public StripeInvoice payInvoice(String invoiceId) throws StripeException;

    public StripeInvoice update(String invoiceId, StripeInvoice invoice) throws StripeException;
}
