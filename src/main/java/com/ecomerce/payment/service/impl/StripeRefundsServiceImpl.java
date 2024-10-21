package com.ecomerce.payment.service.impl;


import com.ecomerce.payment.persistence.model.StripeInvoice;
import com.ecomerce.payment.persistence.model.StripeRefunds;
import com.ecomerce.payment.repository.StripeRefundsRepository;
import com.ecomerce.payment.service.StripeRefundService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeRefundsServiceImpl  implements StripeRefundService {

    @Autowired
    private StripeRefundsRepository stripeRefundsRepository;
    @Autowired
    private StripeInvoiceServiceImpl stripeInvoiceService;

    @Override
    public StripeRefunds findById(String id)  {
        StripeRefunds stripeRefund = stripeRefundsRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        return stripeRefund;
    }

    @Override
    public StripeRefunds save(StripeRefunds stripeRefund) {
        if (stripeRefund.getInvoice() == null) {
            return null;
        }
        return stripeRefundsRepository.save(stripeRefund);
    }

    @Override
    public StripeRefunds update(String refundId, String reason, double amount) {
        StripeRefunds stripeRefund = findById(refundId);
        if (stripeRefund !=null) {
            stripeRefund.setReason(reason);
            stripeRefund.setAmount(amount);
            return stripeRefundsRepository.save(stripeRefund);
        }
        return null;
    }

    @Override
    public void delete(String refundId) {
        stripeRefundsRepository.deleteById(refundId);
    }

    @Override
    public List<StripeRefunds> findAll() {
        return stripeRefundsRepository.findAll();
    }




    @Override
    public StripeRefunds createRefund(String invoiceId, int amount, String reason)throws NullPointerException, StripeException {
        StripeInvoice invoice  = stripeInvoiceService.findInvoiceById(invoiceId);
        if (invoice == null ) {
            throw new NullPointerException("Invoice not found");
        }
        if (invoice.getStatus().equals("paid") && !invoice.getPaymentIntent().isEmpty()) {
            Map<String, Object> prepareData = new HashMap<String, Object>();
            prepareData.put("amount", amount);
            prepareData.put("payment_intent", invoice.getPaymentIntent());

            Refund refund = Refund.create(prepareData);
            if (refund != null) {
                StripeRefunds stripeRefund = new StripeRefunds();
                stripeRefund.setId(refund.getId());
                stripeRefund.setPaymentId(refund.getPaymentIntent());
                stripeRefund.setAmount(amount);
                stripeRefund.setStatus(refund.getStatus());
                stripeRefund.setReason(reason);
                stripeRefund.setInvoice(invoice);
                return stripeRefundsRepository.save(stripeRefund);

            }
        }
        return null;
    }







}