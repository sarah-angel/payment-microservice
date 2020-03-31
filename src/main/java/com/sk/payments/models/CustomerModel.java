package com.sk.payments.models;

import org.springframework.stereotype.Component;

import com.stripe.model.Token;

@Component
public class CustomerModel {
	private String stripeCustomerId;
	private String email;
	private Token stripeToken;
	
	public String getStripeCustomerId() {
		return stripeCustomerId;
	}

	public void setStripeCustomerId(String stripeCustomerId) {
		this.stripeCustomerId = stripeCustomerId;
	}
	
	public Token getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(Token stripeToken) {
		this.stripeToken = stripeToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
