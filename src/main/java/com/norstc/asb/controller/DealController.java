package com.norstc.asb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.norstc.asb.deal.DealService;

@Controller
public class DealController {

	private static final String VIEWS_DEAL_ADD_OR_UPDATE_FORM = "stock/addOrUpdateDealForm";
	private DealService dealService;
	
	@Autowired
	public void setDealService(DealService dealService){
		this.dealService = dealService;
	}

	@RequestMapping("/stock/recorder")
	public String mainRecorderHandler(Model model){
		model.addAttribute("deals",dealService.findAll());
		return "/stock/recorder";
	}
	
	@RequestMapping("/stock/recorder/{id}")
	public String recorderDetailHandler(@PathVariable Integer id, Model model){
		model.addAttribute("deal",dealService.getDealById(id));
		return "/stock/recorder";
	}
	
	@RequestMapping("/stock/recoder/add")
	public String addDealHandler(){
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
}
