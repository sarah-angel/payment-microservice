package com.sk.payments.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk.payments.models.CustomerModel;
import com.sk.payments.services.CustomerService;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSource;
import com.stripe.model.PaymentSourceCollection;
import com.stripe.model.Token;

@RestController
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	Gson gson = new GsonBuilder().create();

	/**
	 * Create a new stripe customer and save card details
	 * Update customer by adding multiple cards
	 * @param customerModel
	 * @return
	 * @throws StripeException
	 */
	@RequestMapping("/customer")
	public String customer(@RequestBody CustomerModel customerModel) throws StripeException {
		
		Customer customer = customerService.customer(customerModel);
		
		//Attach card to customer
		 Map<String, Object> sourceParams = new HashMap<>();
	     sourceParams.put("source", customerModel.getStripeToken().getId());

	     //Re-fetch token from stripe to get fingerprint
	     Token token = Token.retrieve(customerModel.getStripeToken().getId());
	     
	     Boolean cardExists = false;
	     
	     //Check if card already exists for the customer
	     //compare card fingerprints
	     List<PaymentSource> cards = customer.getSources().getData();
	     for(PaymentSource card: cards) {
	    	 String x = gson.toJson(card);
	    	 Card y = gson.fromJson(x, Card.class);

	    	 if(y.getFingerprint().equals(token.getCard().getFingerprint())) {
	    		 cardExists = true;
	    	 }    	
	     };
	     
	     if ( !cardExists ) {
	    	 customer.getSources().create(sourceParams);
    	 	return gson.toJson(customer);
	     }else {
	    	 //TODO: make proper response
	    	 return "{\"error\": \"Card already exists.\"}"; 
	     }	
		
	}
	
	@RequestMapping("/cards")
	public String cards(@RequestBody CustomerModel customerModel) throws StripeException {
		Customer customer = customerService.customer(customerModel);
		
		PaymentSourceCollection cards = customer.getSources();
		
		Gson gson = new GsonBuilder().create();
		return gson.toJson(cards);	
	}
	
	public class Response {
		private String status;
		private String error;
	}
}
