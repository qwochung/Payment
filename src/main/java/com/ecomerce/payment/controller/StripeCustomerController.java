package com.ecomerce.payment.controller;

import com.ecomerce.payment.persistence.model.StripeCustomer;
import com.ecomerce.payment.service.impl.StripeCustomerServiceImpl;
import com.ecomerce.payment.service.impl.StripeInvoiceServiceImpl;
import com.ecomerce.payment.util.RestResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.WebhookEndpoint;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/stripe/customers")

public class StripeCustomerController {


    @Autowired
   private StripeCustomerServiceImpl stripeCustomerService;



    @Autowired
    private StripeInvoiceServiceImpl stripeInvoiceService;



    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<StripeCustomer> createCustomer (@RequestBody StripeCustomer stripeCustomer) throws StripeException {
        if (stripeCustomer != null) {
            return new ResponseEntity<>( stripeCustomerService.createCustomer(stripeCustomer), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<StripeCustomer> getCustomer (@PathVariable("customerId") String customerId) throws Exception {
        if (customerId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(stripeCustomerService.findCustomerById(customerId), HttpStatus.OK);
    }


    @RequestMapping(value = "/{customerId}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomer (@PathVariable("customerId") String customerId) throws Exception {
        if (customerId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        stripeCustomerService.delete(customerId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }




//    @RequestMapping(value = "/{customerId}",method = RequestMethod.PUT)
//    public ResponseEntity<StripeCustomer> updateCustomer (@PathVariable("customerId") String customerId, @RequestBody StripeCustomer stripeCustomer) throws Exception {
//
//        if (customerId.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(  stripeCustomerService.updateCustomer(customerId, stripeCustomer), HttpStatus.OK);
//    }


    @RequestMapping(value = "/{customerId}",method = RequestMethod.PUT)
    public ResponseEntity<StripeCustomer> updateCustomer (@PathVariable("customerId") String customerId, @RequestBody StripeCustomer stripeCustomer) throws Exception {
        List<String> list = List.of("customer.created","customer.updated" );
//        String url = "http://localhost:8080/api/v2/stripe/customers/"+customerId;
        String url = "http://localhost:8080/api/v2/*/";
        WebhookEndpoint webhookEndpoint = new WebhookEndpoint();
        webhookEndpoint.setUrl(url);
        webhookEndpoint.setEnabledEvents(list);

        if (customerId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(  stripeCustomerService.updateCustomer(customerId, stripeCustomer), HttpStatus.OK);
    }




















}
