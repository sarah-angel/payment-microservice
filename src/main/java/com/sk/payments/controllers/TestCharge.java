package com.sk.payments.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

@Controller
public class TestCharge {

	@Value("${STRIPE_SECRET_KEY}")
	String stripeApiKey;
	
	@RequestMapping("/chargex")
	public String charge(@RequestParam("stripeToken") String stripeToken) throws StripeException {
		
		Stripe.apiKey = stripeApiKey;
				
		String token = stripeToken;
		
		ChargeCreateParams params = 
			ChargeCreateParams.builder()
				.setAmount(899L)
				.setCurrency("usd")
				.setDescription("Example charge")
				.setSource(token)
				.build();
		Charge charge = Charge.create(params);
		
		return "result.html";
	}
}
