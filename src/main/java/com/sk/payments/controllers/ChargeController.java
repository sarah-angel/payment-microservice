package com.sk.payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sk.payments.models.ChargeRequest;
import com.sk.payments.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
public class ChargeController {

	@Autowired
    private StripeService paymentsService;
    
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	public Response charge(@RequestBody ChargeRequest chargeRequest)
      throws StripeException {
    	    	
    	chargeRequest.setDescription("Example charge");
        //chargeRequest.setCurrency(Currency.EUR);
        
        Charge charge = paymentsService.charge(chargeRequest);
        
        Response res = new Response();
        res.setStatus(charge.getStatus());
        res.setChargeId(charge.getId());
        res.setBalance_transaction(charge.getBalanceTransaction());
        
        return res;
    }
	
	public class Response {
		private String status;
		private String chargeId;
		private String balance_transaction;
		private String error;
		
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getChargeId() {
			return chargeId;
		}
		public void setChargeId(String chargeId) {
			this.chargeId = chargeId;
		}
		public String getBalance_transaction() {
			return balance_transaction;
		}
		public void setBalance_transaction(String balance_transaction) {
			this.balance_transaction = balance_transaction;
		}
		
		
	}
	
    @ExceptionHandler(StripeException.class)
    public Response handleError(Model model, StripeException ex) {
        Response res = new Response();
    	res.setError(ex.getMessage());
        return res;
    }
}
