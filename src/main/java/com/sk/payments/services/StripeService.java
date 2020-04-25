package com.sk.payments.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sk.payments.models.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripeService {
 
    @Value("${STRIPE_SECRET_KEY}")
    String secretKey;
     
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    
    public Charge charge(ChargeRequest chargeRequest) 
      throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        
        //Use a saved card if available
        if ( chargeRequest.getStripeCustomerId() != null && chargeRequest.getStripeCustomerId() != "") {
        	chargeParams.put("customer", chargeRequest.getStripeCustomerId());
        	
        	//Use a card other than the default
        	if( chargeRequest.getCardId() != null && chargeRequest.getCardId() != "")
        		chargeParams.put("source", chargeRequest.getCardId());
        }else
        
        	chargeParams.put("source", chargeRequest.getStripeToken().getId());
        
        return Charge.create(chargeParams);
    }
}
