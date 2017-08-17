package com.norstc.asb.controller;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.norstc.asb.deal.DealService;
import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerService;
import com.norstc.asb.stock.StockEntity;
import com.norstc.asb.stock.StockService;

@Controller

public class StockController {
	private Logger log = Logger.getLogger(StockController.class);
	
	private static final String VIEWS_TARGET_ADD_OR_UPDATE_FORM = "stock/addOrUpdateTargetForm";
	private StockService stockService;
	private OwnerService ownerService;
	private DealService dealService;
	
	@Autowired
	public void setDealService(DealService dealService){
		this.dealService = dealService;
	}
	
	@Autowired
	public void setOwnerService(OwnerService ownerService){
		this.ownerService = ownerService;
	}
	
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
	public String allTargetHandler(Model model, Principal principal){
		String username = principal.getName();
		if(username.equals("annymouseUser")){
			log.info("log in as:  " + username);
			model.addAttribute("stocks",stockService.findAll());
		}else{
			
			log.info("have logged in as :  " + username);
			OwnerEntity owner = this.ownerService.findByUsername(username);
			model.addAttribute("stocks",stockService.findByOwner(owner));
		}
		
		return "/stock/target";
	}
	
	@RequestMapping("/stock/target/{id}")
	public String mainTargetHandler(@PathVariable Integer id, Model model){
		model.addAttribute("stock",stockService.getStockById(id));
		return "/stock/target";
	}
	
	//增加target
	//显示表单
	@RequestMapping(value = "/stock/target/add", method = RequestMethod.GET)
	public String actionTargetHandler(Map<String, Object> model, Principal principal){
		StockEntity stockEntity = new StockEntity();
		String username = principal.getName();
		OwnerEntity ownerEntity = ownerService.findByUsername(username);
		stockEntity.setOwner(ownerEntity);
		
		model.put("stockEntity", stockEntity);
		return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
	}
	//提交表单
	@RequestMapping(value="/stock/target/add", method = RequestMethod.POST)
	public String processAddForm(@Valid StockEntity stockEntity, BindingResult result,Principal principal){
		if(result.hasErrors()){
			log.info("processAdd error: " + result.toString());
			return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
		}else{
			
			
			
			this.stockService.add(stockEntity);
			log.info("processAdd ok: , stockEntity is: " + stockEntity.getStockCode());
			return "redirect:/stock/target/"+stockEntity.getId();
		}
	}
	
	//修改target
	//显示修改表单
	@RequestMapping(value="/stock/target/{id}/update", method= RequestMethod.GET)
	public String updateTargetHandler(@PathVariable Integer id, Map<String,Object> modelMap,Model model){
		StockEntity stockEntity = new StockEntity();
		stockEntity = stockService.getStockById(id);
		log.info("updateTarget: " + stockEntity.getOwner());
		modelMap.put("stockEntity", stockEntity);
		Boolean isUpdate = true;
		model.addAttribute("isUpdate", isUpdate);
		return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
	}
	//提交修改表单
	@RequestMapping(value="/stock/target/{id}/update",method=RequestMethod.POST)
	public String processUpdateTargetHandler(@PathVariable Integer id, @Valid StockEntity stockEntity,BindingResult result,Principal principal){
		if(result.hasErrors()){
			log.info("processUpdateTarget: " + result.toString());
			return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
		}else{
			StockEntity oldStock = stockService.getStockById(id);
			oldStock.setAiPrice(stockEntity.getAiPrice());
			
			this.stockService.add(oldStock);
			return "redirect:/stock/target/" + oldStock.getId();
		}
	}
	
	
	//删除target
	@RequestMapping(value="/stock/target/{id}/delete",method=RequestMethod.GET)
	public String deleteTargetHandler(@PathVariable Integer id){
		StockEntity stockEntity = stockService.getStockById(id);
		stockService.deleteStock(stockEntity);
		return "redirect:/stock/target";
	}
	
	@RequestMapping("/stock/balance")
	public String mainBalanceHandler(Model model, Principal principal){
		String username = principal.getName();
		OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
		model.addAttribute("owner", ownerEntity);
		model.addAttribute("deals",dealService.findByOwner(ownerEntity));
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
