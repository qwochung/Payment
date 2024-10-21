package com.ecomerce.payment.controller;

import com.ecomerce.payment.persistence.model.StripeInvoiceItem;
import com.ecomerce.payment.service.StripeInvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/stripe/items")
public class StripeInvoiceItemController {
    @Autowired
    private StripeInvoiceItemService stripeInvoiceItemService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<StripeInvoiceItem> createItem ( @RequestBody StripeInvoiceItem item) throws Exception {
        if (item.getInvoice() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceItemService.save(item), HttpStatus.OK);
    }


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ResponseEntity<List<StripeInvoiceItem>> getAllItems () throws Exception {
        return new ResponseEntity<>(stripeInvoiceItemService.findAll(), HttpStatus.OK);
    }



    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<StripeInvoiceItem> getItem (@PathVariable String itemId) throws Exception {
        if (itemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceItemService.findById(itemId), HttpStatus.OK);
    }


    @RequestMapping(value = "/invoice/{invoiceId}", method = RequestMethod.GET)
    public ResponseEntity<List<StripeInvoiceItem>> getInvoiceItem (@PathVariable String invoiceId) throws Exception {
        if (invoiceId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(stripeInvoiceItemService.findByInvoiceId(invoiceId), HttpStatus.OK);
    }



    @RequestMapping(value = "/{itemId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<StripeInvoiceItem> deleteItem (@PathVariable String itemId) throws Exception {
        if (itemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        stripeInvoiceItemService.delete(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
