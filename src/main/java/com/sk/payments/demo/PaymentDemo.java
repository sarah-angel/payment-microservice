package com.sk.payments.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentDemo {
	
	@RequestMapping("demo")
	public String demo() {
		//checkout.jsp working!!
		return "payment.html";
	}
}
