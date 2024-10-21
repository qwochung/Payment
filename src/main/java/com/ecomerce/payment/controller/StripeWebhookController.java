package com.ecomerce.payment.controller;

import com.ecomerce.payment.persistence.model.StripeCustomer;
import com.ecomerce.payment.persistence.model.StripeWebHook;
import com.ecomerce.payment.service.impl.StripeCustomerServiceImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.WebhookEndpoint;
import com.stripe.param.WebhookEndpointCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/stripe/webhooks")

public class StripeWebhookController {




//
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ResponseEntity<WebhookEndpoint> listienToCustomer (@RequestBody StripeWebHook stripeWebHook) throws StripeException {
//        if (stripeWebHook != null) {
////            List<String> list = List.of("customer.created","customer.updated" );
//////          String url = "http://localhost:8080/api/v2/stripe/customers/"+customerId;
//////            String url = "http://webhook-payment.ap-southeast-2.elasticbeanstalk.com//api/v2/stripe/customers";
////
////            WebhookEndpoint webhookEndpoint = new WebhookEndpoint();
////            webhookEndpoint.setUrl(stripeWebHook.getUrl());
////            webhookEndpoint.setEnabledEvents(stripeWebHook.getEnabled_events());
//            Ena
//
//            WebhookEndpointCreateParams params = WebhookEndpointCreateParams.builder()
//                    .setUrl("https://yourdomain.com/api/v2/stripe/customers") // URL webhook của anh
//                    .addEnabledEvent(new EnableEvenent ("payment_intent.canceled")) // Các sự kiện mà anh muốn nhận
//                    .addEnabledEvent("payment_intent.created")
//                    .build();
//
//            WebhookEndpoint webhookEndpoint = WebhookEndpoint.create(params);
//
//
//            return new ResponseEntity<>(webhookEndpoint, HttpStatus.OK);
//
//            return new ResponseEntity<>( webhookEndpoint,HttpStatus.OK);
//        }
//        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//    }

//
//    @PostMapping
//    public ResponseEntity<String> processStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//        String endpointSecret = "your_webhook_secret";
//        Event event;
//        System.out.println(payload);
//        return null;
//    }

}
