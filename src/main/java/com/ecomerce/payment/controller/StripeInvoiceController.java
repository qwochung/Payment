package com.ecomerce.payment.controller;

import com.ecomerce.payment.persistence.model.StripeInvoice;
import com.ecomerce.payment.persistence.model.StripeRefunds;
import com.ecomerce.payment.service.StripeInvoiceService;

import com.ecomerce.payment.service.impl.StripeRefundsServiceImpl;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/stripe/invoice")

public class StripeInvoiceController {


    @Autowired
    private StripeInvoiceService stripeInvoiceService;

    @Autowired
    private StripeRefundsServiceImpl stripeRefundsService;

    @RequestMapping(value ="/customers/{customerId}" , method = RequestMethod.POST)
    public ResponseEntity<StripeInvoice> createInvoice(@PathVariable("customerId")  String customerId) throws StripeException, NullPointerException {
        System.out.println(customerId);
        if (customerId == null || customerId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new  ResponseEntity<>(stripeInvoiceService.createInvoice(customerId), HttpStatus.CREATED);

    }




    @RequestMapping(value ="/customers/{customerId}" , method = RequestMethod.GET )
    public ResponseEntity<List<StripeInvoice>> getInvoiceByCustomer(@PathVariable("customerId")  String customerId) throws StripeException {
        if (customerId == null || customerId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceService.findInvoiceByCustomer(customerId), HttpStatus.OK);
    }




    @RequestMapping(value = "/{invoiceId}")
    public ResponseEntity<StripeInvoice> getInvoiceById(@PathVariable("invoiceId")  String invoiceId) throws StripeException {
        if (invoiceId == null || invoiceId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceService.findInvoiceById(invoiceId), HttpStatus.OK);
    }



    @RequestMapping(value = "/{invoiceId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteInvoice(@PathVariable("invoiceId")  String invoiceId) throws StripeException {
        if (invoiceId == null || invoiceId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        stripeInvoiceService.delete(invoiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @RequestMapping(value ="/{invoiceId}/finalize" , method = RequestMethod.PUT)
    public ResponseEntity<StripeInvoice> finalizeItem(@PathVariable("invoiceId") String invoiceId) throws StripeException {
        if (invoiceId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceService.finalize(invoiceId), HttpStatus.OK);

    }





    @RequestMapping(value = "/{invoiceId}/pay", method = RequestMethod.POST)
    public ResponseEntity<StripeInvoice> pay (@PathVariable String invoiceId) throws StripeException {
        if (invoiceId == null || invoiceId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceService.payInvoice(invoiceId), HttpStatus.OK);
    }


    @RequestMapping(value = "/{invoiceId}/refunds", method = RequestMethod.POST)
    public ResponseEntity<StripeRefunds> refundItem(@PathVariable("invoiceId") String invoiceId,
                                                    @RequestParam("amount") int amount,
                                                    @RequestParam("reason") String reason) throws StripeException {
        if (invoiceId.isEmpty() || amount <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeRefundsService.createRefund(invoiceId, amount, reason), HttpStatus.OK);
    }












}
