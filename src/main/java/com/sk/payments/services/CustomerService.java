package com.sk.payments.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sk.payments.models.CustomerModel;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

@Service
public class CustomerService {
	
	 @Value("${STRIPE_SECRET_KEY}")
	    String secretKey;
	     
	    @PostConstruct
	    public void init() {
	        Stripe.apiKey = secretKey;
	    }
	    
	    public Customer customer(CustomerModel customerModel) 
	      throws StripeException {
	    	
	    	//if customer is given in request then retrieve existing
	    	if(customerModel.getStripeCustomerId() != null) {
	    		Customer customer = Customer.retrieve(customerModel.getStripeCustomerId());
	    		
	    		return customer;
	    	}else {
	    		Map<String, Object> customerParams = new HashMap<>();
	    		customerParams.put("email", customerModel.getEmail());        
	       
	    		return Customer.create(customerParams);
	    	}
	    }
}

