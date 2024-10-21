package com.ecomerce.payment.service.impl;

import com.ecomerce.payment.persistence.model.StripeCustomer;
import com.ecomerce.payment.repository.StripeCustomerRepository;
import com.ecomerce.payment.service.StripeCustomerService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service

public class StripeCustomerServiceImpl implements StripeCustomerService {

    @Autowired
    private StripeCustomerRepository customerRepository;


//    @Autowired
//    private StripeInvoiceRepository stripeInvoiceRepository;


    public StripeCustomer createCustomer(StripeCustomer stripeCustomer) throws StripeException, NullPointerException {
        if (!(stripeCustomer.getName().isEmpty())) {
            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("name", stripeCustomer.getName());
            prepareData.put("balance", stripeCustomer.getBalance());
            prepareData.put("email", stripeCustomer.getEmail());
            prepareData.put("phone", stripeCustomer.getPhone());
            if (stripeCustomer.getPayment_method()!= null){
                prepareData.put("payment_method", stripeCustomer.getPayment_method());
                stripeCustomer.getInvoice_settings().put("default_payment_method", stripeCustomer.getPayment_method());
                prepareData.put("invoice_settings", stripeCustomer.getInvoice_settings());
            }
            Customer customer = Customer.create(prepareData);
            if (!Objects.isNull(customer)) {
                stripeCustomer.setId(customer.getId());
                customerRepository.save(stripeCustomer);
            }
            return stripeCustomer;
        }
        else return null;

    }



    @Override
    public StripeCustomer findCustomerById(String customerId) throws Exception, StripeException{
        StripeCustomer stripeCustomer = customerRepository.findCustomerById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return stripeCustomer;
    }


    @Override
    public StripeCustomer updateCustomer( String customerId ,StripeCustomer newCustomer) throws Exception, StripeException{
        if (newCustomer == null || customerId.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        StripeCustomer customer = customerRepository.findCustomerById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Map<String, Object> prepareData =new HashMap<>();
        if (newCustomer.getName() != null) {
            prepareData.put("name", newCustomer.getName());
            customer.setName(newCustomer.getName());
        }
        if (newCustomer.getEmail() != null) {
            prepareData.put("email", newCustomer.getEmail());
            customer.setEmail(newCustomer.getEmail());
        }
        if (newCustomer.getPhone() != null) {
            prepareData.put("phone", newCustomer.getPhone());
            customer.setPhone(newCustomer.getPhone());
        }
        if (newCustomer.getCurrency() != null) {
            prepareData.put("currency", newCustomer.getCurrency());
            customer.setCurrency(newCustomer.getCurrency());
        }
        try{
            Customer.retrieve(customerId).update(prepareData);
            return customerRepository.save(customer);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }


    @Override
    public void delete(String stripeCustomerId) throws Exception, StripeException{
        if (stripeCustomerId.isEmpty()) {
            throw new RuntimeException("Customer id is empty");
        }
        customerRepository.deleteById(stripeCustomerId);
        Customer.retrieve(stripeCustomerId).delete();
    }

//
//
//    public StripePayment createPaymentIntent (StripePayment payment) throws StripeException {
//        if (payment != null) {
//            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//                    .setAmount(payment.getAmount())
//                    .setCurrency(payment.getCurrency())
//                    .setConfirm(payment.isConfirm())
//                    .setDescription("Paid " + payment.getAmount() + payment.getCurrency())
//                    .setPaymentMethod(payment.getPayment_method())
//                    .setReturnUrl(payment.getReturn_url())
//                    .build();
//            try {
//                PaymentIntent paymentIntent = PaymentIntent.create(params);
//                if (paymentIntent != null) {
//                    payment.setId(paymentIntent.getId());
//                    return payment;
//                }
//            } catch (StripeException e) {
//                System.err.println("Stripe API error: " + e.getMessage());
//                throw new RuntimeException("Failed to create PaymentIntent: " + e.getMessage());
//
//            }
//        } else {
//            throw new RuntimeException("Stripe payment is null");
//        }
//        return null;
//    }
//
//
//
//
//
//
//
//


    /**Sub method */
    private static Map<String, Object> getStringObjectMap(StripeCustomer newCustomer) {
        Map<String, Object> prepareData = new HashMap<>();
        if (newCustomer.getName() != null) {
            prepareData.put("name", newCustomer.getName());
        }
        if (newCustomer.getEmail() != null) {
            prepareData.put("email", newCustomer.getEmail());
        }
        if (newCustomer.getPhone() != null) {
            prepareData.put("phone", newCustomer.getPhone());
        }
        if (newCustomer.getCurrency() != null) {
            prepareData.put("currency", newCustomer.getCurrency());
        }
        return prepareData;
    }



















//
//    public StripeCharge charge(StripeCharge chargeRequest) throws StripeException {
//        chargeRequest.setSuccess(false);
//        Map<String, Object> param = new HashMap<>();
//        param.put("amount", chargeRequest.getAmount());
//        param.put("currency", "USD");
//        param.put("description", "Payment for id " + chargeRequest.getInfor()
//                .getOrDefault("ID_TAG", " "));
//        param.put("source", chargeRequest.getStripeToken());
//
//        Map<String, Object> metaData= new HashMap<>();
//        metaData.put("id", chargeRequest.getChargeId());
//        metaData.putAll(chargeRequest.getInfor());
//
//        param.put("metadata", metaData);
//
//        Charge charge= Charge.create(metaData);
//        chargeRequest.setMessage(charge.getOutcome().getSellerMessage());
//
//        if (charge.getPaid()){
//            chargeRequest.setChargeId(charge.getId());
//            chargeRequest.setSuccess(true);
//        }
//
//        return chargeRequest;
//    };
//






}
