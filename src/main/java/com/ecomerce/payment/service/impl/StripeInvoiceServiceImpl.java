package com.ecomerce.payment.service.impl;


import com.ecomerce.payment.persistence.model.StripeInvoice;
import com.ecomerce.payment.persistence.model.StripeInvoiceItem;
import com.ecomerce.payment.repository.StripeCustomerRepository;
import com.ecomerce.payment.repository.StripeInvoiceRepository;
import com.ecomerce.payment.service.StripeInvoiceService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeInvoiceServiceImpl implements StripeInvoiceService {

    @Autowired
    private StripeInvoiceRepository stripeInvoiceRepository;

    @Autowired
    private StripeCustomerRepository stripeCustomerRepository;
    @Qualifier("webRequest")
    @Autowired
    private WebRequest webRequest;


    public StripeInvoice createInvoice (String customerId) throws NullPointerException, StripeException {
        Customer customer = Customer.retrieve(customerId);
//        if (customer.getInvoiceSettings().getDefaultPaymentMethod().isEmpty()) {
//            //update logic here
//            return null;
//
//        }
        if (stripeCustomerRepository.existsById(customerId)) {
            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("customer", customerId);
            if (customer.getCurrency() != null) {
                prepareData.put("currency", customer.getCurrency());
            }
            Invoice invoice = Invoice.create(prepareData);
            if (invoice != null) {
                StripeInvoice stripeInvoice = new StripeInvoice(invoice);
                return stripeInvoiceRepository.save(stripeInvoice);

            }

        }

        else {
            throw new RuntimeException("Stripe customer not found: " + customerId);

        }
        return null;
    }



    @Override
    public StripeInvoice save(StripeInvoice invoice) throws StripeException{
        if (stripeInvoiceRepository.existsById(invoice.getId())) {
            return stripeInvoiceRepository.save(invoice);
        }
        else return null;
    }


    @Override
    public List<StripeInvoice> findInvoiceByCustomer(String customer) throws StripeException, NullPointerException{
        return stripeInvoiceRepository.findByCustomerId(customer);

    }

    public StripeInvoice findInvoiceById(String invoiceId) throws StripeException{
        StripeInvoice stripeInvoice = stripeInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Stripe invoice not found: " + invoiceId));
        return stripeInvoice;
    }



    @Override
    public StripeInvoice update( String invoiceId,StripeInvoice inputInvoice) throws StripeException {
        if (stripeInvoiceRepository.existsById(invoiceId)) {
            Invoice invoice = Invoice.retrieve(invoiceId);

            if (inputInvoice.getId()!= invoiceId) inputInvoice.setId(invoiceId);
            if (inputInvoice.getCustomerId()!=null) inputInvoice.setCustomerId(invoice.getCustomer());
            if (inputInvoice.getCurrency()!=null) inputInvoice.setCurrency(invoice.getCurrency());
            stripeInvoiceRepository.save(inputInvoice);
        }
        return null;
    }



    @Override
    public void delete(String invoiceId) throws StripeException{
        Invoice invoice = Invoice.retrieve(invoiceId);
        if (invoice.getStatus().equals("draft")) {
            invoice.delete();
            stripeInvoiceRepository.deleteById(invoiceId);
        }
        else
            throw new RuntimeException("You can only delete draft invoices");
    }





    @Override
    public StripeInvoice finalize(String invoiceId) throws StripeException{
        StripeInvoice stripeInvoice = stripeInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Stripe invoice not found"));
        Invoice invoice = Invoice.retrieve(invoiceId).finalizeInvoice();
        stripeInvoice.setStatus(invoice.getStatus());

        return stripeInvoiceRepository.save(stripeInvoice);
    }


    @Override
    public StripeInvoice payInvoice(String invoiceId) throws StripeException{
        if (invoiceId == null || invoiceId.isEmpty()) {
            return null;
        }

        StripeInvoice stripeInvoice = stripeInvoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Stripe invoice not found"));
        if (stripeInvoice.getStatus().equals("open")) {
            Invoice invoice = Invoice.retrieve(invoiceId).pay();
            stripeInvoice.setStatus(invoice.getStatus());
            stripeInvoice.setChargeId(invoice.getCharge());
            stripeInvoice.setPaymentIntent(invoice.getPaymentIntent());

            return stripeInvoiceRepository.save(stripeInvoice);
        }

        return null;
    }




    public StripeInvoice deleteInvoiceItem(String invoiceItemId) throws StripeException{
        return null;
    }


}
