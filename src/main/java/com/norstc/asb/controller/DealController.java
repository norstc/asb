package com.norstc.asb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.norstc.asb.deal.DealService;

@Controller
public class DealController {

	private static final String VIEWS_DEAL_ADD_OR_UPDATE_FORM = "stock/addOrUpdateDealForm";
	private DealService dealService;

	@RequestMapping("/stock/recorder")
	public String mainRecorderHandler(){
		return "/stock/recorder";
	}
	
}
