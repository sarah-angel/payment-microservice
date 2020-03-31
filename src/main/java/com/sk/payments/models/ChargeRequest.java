package com.sk.payments.models;

import org.springframework.stereotype.Component;

import com.stripe.model.Token;

@Component
public class ChargeRequest {
	private int amount;
    private Currency currency;
    private Token stripeToken;
    private String stripeCustomerId;
    private String cardId;	
    private String description;

    public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

    public String getStripeCustomerId() {
		return stripeCustomerId;
	}
	public void setStripeCustomerId(String stripeCustomerId) {
		this.stripeCustomerId = stripeCustomerId;
	}
	public enum Currency {
        EUR, USD;
    }
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = (int) (Double.parseDouble(amount) * 100); //in cents
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Token getStripeToken() {
		return stripeToken;
	}
	public void setStripeToken(Token stripeToken) {
		this.stripeToken = stripeToken;
	}
}