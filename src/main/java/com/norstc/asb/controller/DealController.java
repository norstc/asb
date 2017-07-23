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

import com.norstc.asb.deal.DealEntity;
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
	
	//显示买入表单
	@RequestMapping(value="/stock/recoder/buy" ,method=RequestMethod.GET)
	public String addDealHandler(Map<String,Object> model){
		DealEntity dealEntity = new DealEntity();
		model.put("dealEntity", dealEntity);
		model.put("buyOrSell", true);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//提交买入表单
	@RequestMapping(value="/stock/recoder/buy", method=RequestMethod.POST)
	public String processAddForm(@Valid DealEntity dealEntity, BindingResult result){
		if(result.hasErrors()){
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			this.dealService.add(dealEntity);
			return "redirect:/stock/recorder/"+dealEntity.getId();
		}
	}
	
	//显示卖出表单
	@RequestMapping(value="/stock/deal/{id}/sell",method=RequestMethod.GET)
	public String sellHandler(@PathVariable Integer id, Map<String,Object> model){
		DealEntity dealEntity = new DealEntity();
		dealEntity = dealService.getDealById(id);
		model.put("dealEntity", dealEntity);
		model.put("buyOrSell",false);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//提交卖出表单
	@RequestMapping(value = "/stock/deal/{id}/sell", method=RequestMethod.POST)
	public String processSellHandler(@Valid DealEntity dealEntity, BindingResult result){
		if(result.hasErrors()){
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			this.dealService.add(dealEntity);
			return "redirect:/stock/recorder/" + dealEntity.getId();
		}
	}
	//修改deal
	@RequestMapping(value="/stock/deal/{id}/update",method=RequestMethod.GET)
	public String updatedealHandler(@PathVariable Integer id, Map<String,Object> model){
		DealEntity dealEntity = new DealEntity();
		dealEntity = dealService.getDealById(id);
		model.put("dealEntity", dealEntity);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//修改deal， 处理
	@RequestMapping(value= "/stock/deal/{id}/update",method=RequestMethod.POST)
	public String precessUpdateHandler(@Valid DealEntity dealEntity, BindingResult result){
		if(result.hasErrors()){
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			this.dealService.add(dealEntity);
			return "redirect:/stock/recorder/" + dealEntity.getId();
		}
	}
	//删除deal
	@RequestMapping(value="/stock/deal/{id}/delete",method=RequestMethod.GET)
	public String deleteDealHandler(@PathVariable Integer id){
		DealEntity dealEntity = dealService.getDealById(id);
		dealService.deleteDeal(dealEntity);
		return "redirect:/stock/recorder/";
	}
}
