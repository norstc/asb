package com.norstc.asb.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.norstc.asb.stock.StockEntity;
import com.norstc.asb.stock.StockService;

@Controller
public class StockController {
	private static final String VIEWS_TARGET_ADD_OR_UPDATE_FORM = "stock/addOrUpdateTargetForm";
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
	
	@RequestMapping("/stock/target")
	public String allTargetHandler(Model model){
		model.addAttribute("stocks",stockService.findAll());
		return "/stock/target";
	}
	
	@RequestMapping("/stock/target/{id}")
	public String mainTargetHandler(@PathVariable Integer id, Model model){
		model.addAttribute("stock",stockService.getStockById(id));
		return "/stock/target";
	}
	
	//显示表单
	@RequestMapping(value = "/stock/target/add", method = RequestMethod.GET)
	public String actionTargetHandler(Map<String, Object> model){
		StockEntity stockEntity = new StockEntity();
		model.put("stockEntity", stockEntity);
		return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
	}
	//提交表单
	@RequestMapping(value="/stock/target/add", method = RequestMethod.POST)
	public String processAddForm(@Valid StockEntity stockEntity, BindingResult result){
		if(result.hasErrors()){
			return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
		}else{
			this.stockService.add(stockEntity);
			System.out.println("stockEntity is " + stockEntity.getStockCode());
			return "redirect:/stock/target/"+stockEntity.getId();
		}
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
