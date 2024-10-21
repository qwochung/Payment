package com.ecomerce.payment.persistence.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StripeWebHook {
    List<String> enabled_events = new ArrayList<>();
    String url;

}
