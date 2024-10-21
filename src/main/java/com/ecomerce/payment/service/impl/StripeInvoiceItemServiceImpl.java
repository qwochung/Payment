package com.ecomerce.payment.service.impl;

import com.ecomerce.payment.persistence.model.StripeInvoice;
import com.ecomerce.payment.persistence.model.StripeInvoiceItem;
import com.ecomerce.payment.repository.StripeInvoiceItemRepository;
import com.ecomerce.payment.service.StripeInvoiceItemService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeInvoiceItemServiceImpl implements StripeInvoiceItemService {

    @Autowired
    StripeInvoiceServiceImpl stripeInvoiceServiceImpl;

    @Autowired
    StripeInvoiceItemRepository stripeInvoiceItemRepository;



    @Override
    public StripeInvoiceItem save(StripeInvoiceItem item) throws StripeException{
        if (item.getInvoice().getId().isEmpty()) {
            throw new  RuntimeException("Invoice Id cannot be empty");
        }

        else{
            String invoiceId = item.getInvoice().getId();
            Customer.retrieve(item.getCustomerId());
            Invoice.retrieve(invoiceId);
            StripeInvoice invoice = stripeInvoiceServiceImpl.findInvoiceById(invoiceId);

            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("customer", item.getCustomerId());
            prepareData.put("invoice", invoiceId);
            prepareData.put("amount", item.getAmount());

            InvoiceItem invoiceItem = InvoiceItem.create(prepareData);
            if (invoiceItem != null) {
                item.setId(invoiceItem.getId());
                item.setInvoice(invoice);
                invoice.setTotal(invoice.getTotal() + item.getAmount());
                stripeInvoiceServiceImpl.save(invoice);
                return stripeInvoiceItemRepository.save(item);

            }
        }


        return null;
    }



    @Override
    public StripeInvoiceItem findById(String id) throws Exception, StripeException {
        if(id.isEmpty()){
            throw new  RuntimeException("Invoice Id cannot be empty");
        }
        return stripeInvoiceItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public List<StripeInvoiceItem> findByInvoiceId(String invoiceId) throws Exception, StripeException{
        if(invoiceId.isEmpty()){
            throw new  RuntimeException("Invoice Id cannot be empty");
        }
        return stripeInvoiceItemRepository.findByInvoiceId(invoiceId);
    }

    @Override
    public List<StripeInvoiceItem> findAll() throws Exception, StripeException {
        return stripeInvoiceItemRepository.findAll();
    }



    @Override
    public StripeInvoiceItem update(StripeInvoiceItem stripeInvoiceItem) throws Exception, StripeException {
        return null;
    }


    @Override
    public void delete(String id) throws Exception, StripeException {
        InvoiceItem item = InvoiceItem.retrieve(id);
        StripeInvoice stripeInvoice=  stripeInvoiceServiceImpl.findInvoiceById(item.getInvoice());
        stripeInvoice.setTotal(stripeInvoice.getTotal() - item.getAmount());
        stripeInvoiceServiceImpl.save(stripeInvoice);
        item.delete();
        stripeInvoiceItemRepository.deleteById(id);
    }

}
