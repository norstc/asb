package com.norstc.asb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.norstc.asb.stock.StockService;

@Controller
public class StockController {
	private StockService stockService;
	
	@Autowired
	public void setStockService(StockService stockService){
		this.stockService = stockService;
	}
	
	// main part
	
	@RequestMapping("/stock/select")
	public String mainSelectHandler(){
		return "/stock/select";
	}
	
	@RequestMapping("/stock/target/{id}")
	public String mainTargetHandler(@PathVariable Integer id, Model model){
		model.addAttribute("stock",stockService.getStockById(id));
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
