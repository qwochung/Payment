package com.ecomerce.payment.persistence.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.apiKey}")
    private String secretKey;

    @PostConstruct
    public void stripe() {
        Stripe.apiKey = secretKey;
    }
}
