package com.ecomerce.payment.service;

import com.ecomerce.payment.persistence.model.StripeInvoiceItem;
import com.stripe.exception.StripeException;

import java.util.List;

public interface StripeInvoiceItemService {
    public StripeInvoiceItem save( StripeInvoiceItem item) throws Exception, StripeException;
    public StripeInvoiceItem update(StripeInvoiceItem stripeInvoiceItem) throws Exception, StripeException;
    public StripeInvoiceItem findById(String id) throws Exception, StripeException;
    public List<StripeInvoiceItem> findAll() throws Exception, StripeException;
    public void delete(String id) throws Exception,StripeException;
    public List<StripeInvoiceItem> findByInvoiceId(String invoiceId) throws Exception, StripeException;

}
