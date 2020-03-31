package com.sk.payments.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.stripe.model.PaymentSourceCollection;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/customer")
	public String customer(@RequestBody CustomerModel customerModel) throws StripeException {
		
		Customer customer = customerService.customer(customerModel);
		
		//Attach card to customer
		 Map<String, Object> sourceParams = new HashMap<>();
	     sourceParams.put("source", customerModel.getStripeToken());
	        
	     Card card = (Card) customer.getSources().create(sourceParams);
	        
		Gson gson = new GsonBuilder().create();
		return gson.toJson(customer);
		
	}
	
	@RequestMapping("/cards")
	public String cards(@RequestBody CustomerModel customerModel) throws StripeException {
		Customer customer = customerService.customer(customerModel);
		
		PaymentSourceCollection cards = customer.getSources();
		
		Gson gson = new GsonBuilder().create();
		return gson.toJson(cards);	
	}
}
