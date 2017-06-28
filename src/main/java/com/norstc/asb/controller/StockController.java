package com.norstc.asb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StockController {
	// main part
	
	@RequestMapping("/stock/select")
	public String mainSelectHandler(){
		return "/stock/select";
	}
	
	@RequestMapping("/stock/target")
	public String mainTargetHandler(){
		return "/stock/target";
	}
	
	@RequestMapping("/stock/recorder")
	public String mainRecorderHandler(){
		return "/stock/recorder";
	}
	
	@RequestMapping("/stock/balance")
	public String mainBalanceHandler(){
		return "/stock/balance";
	}
	
	@RequestMapping("/stock/another")
	public String mainAnotherHandler(){
		return "/stock/another";
	}
	
	@RequestMapping("/stock/secret")
	public String mainSecretHandler(){
		return "/stock/secret";
	}
	

}
