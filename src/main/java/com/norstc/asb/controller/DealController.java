package com.norstc.asb.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.norstc.asb.deal.DealEntity;
import com.norstc.asb.deal.DealService;
import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerService;
import com.norstc.asb.stock.StockService;

@Controller
public class DealController {
	private Logger log = Logger.getLogger(DealController.class);
	private static final String VIEWS_DEAL_ADD_OR_UPDATE_FORM = "stock/addOrUpdateDealForm";
	private DealService dealService;
	private OwnerService ownerService;	
	private StockService stockService;
	
	@Autowired
	public void setStockService(StockService stockService){
		this.stockService = stockService;
	}
	
	@Autowired
	public void setOwnerService(OwnerService ownerService){
		this.ownerService = ownerService;
	}
	@Autowired
	public void setDealService(DealService dealService){
		this.dealService = dealService;
	}

	
	
	
	@RequestMapping("/stock/recorder")
	public String mainRecorderHandler(Model model,Principal principal){
		String username = principal.getName();
		OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
		model.addAttribute("deals",dealService.findByOwner(ownerEntity));
		return "/stock/recorder";
	}
	
	@RequestMapping("/stock/recorder/{id}")
	public String recorderDetailHandler(@PathVariable Integer id, Model model){
		model.addAttribute("deal",dealService.getDealById(id));
		return "/stock/recorder";
	}
	
	//显示买入表单
	@RequestMapping(value="/stock/recoder/buy",method=RequestMethod.GET)
	public String addDealHandler(Map<String,Object> modelMap, Model model,Principal principal){
		String username = principal.getName();
		OwnerEntity ownerEntity = ownerService.findByUsername(username);
		DealEntity dealEntity = new DealEntity();
		dealEntity.setBuyOrSell(true);
		log.info("addDealHandler: dealEntity.getBuyOrSell(): " + dealEntity.getBuyOrSell());
		Boolean isUpdate = false;
		
		modelMap.put("dealEntity", dealEntity);
		model.addAttribute("stocks", stockService.findByOwner(ownerEntity));
		model.addAttribute("isUpdate", isUpdate);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//提交买入表单
	@RequestMapping(value="/stock/recoder/buy",method=RequestMethod.POST)
	public String processAddForm(@Valid DealEntity dealEntity, BindingResult result, Principal principal){
		if(result.hasErrors()){
			log.info("processAddForm result has error: dealEntity.getBuyOrSell(): " + dealEntity.getBuyOrSell() +"results : " + result.toString());
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			log.info("processAddForm without error: dealEntity.getBuyOrSell(): " + dealEntity.getBuyOrSell());
			String username = principal.getName();
			OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
			dealEntity.setOwner(ownerEntity);
			this.dealService.add(dealEntity);
			return "redirect:/stock/recorder/"+dealEntity.getId();
		}
	}
	
	//显示卖出表单
	@RequestMapping(value="/stock/deal/{id}/sell",method=RequestMethod.GET)
	public String sellHandler(@PathVariable Integer id, Map<String,Object> model){
		DealEntity dealEntity = new DealEntity();
		dealEntity = dealService.getDealById(id);
		dealEntity.setBuyOrSell(false);
		model.put("dealEntity", dealEntity);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//提交卖出表单
	@RequestMapping(value = "/stock/deal/{id}/sell", method=RequestMethod.POST)
	public String processSellHandler(@Valid DealEntity dealEntity, BindingResult result, @PathVariable Integer id, Principal principal){
		if(result.hasErrors()){
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			log.info("process sell , after post dealEntity id is: " + dealEntity.getId());
			String username = principal.getName();
			OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
			dealEntity.setId(id);
			log.info("process sell dealEntity id is : " + dealEntity.getId());
			DealEntity oldDeal = dealService.getDealById(id);
			oldDeal.setSellTime(dealEntity.getSellTime());
			oldDeal.setSellPrice(dealEntity.getSellPrice());
			oldDeal.setSellQuantity(dealEntity.getSellQuantity());
			oldDeal.setBuyOrSell(dealEntity.getBuyOrSell());
			if(oldDeal.getBuyQuantity() > dealEntity.getSellQuantity()){  //not sell all
				DealEntity newDeal = new DealEntity();
				newDeal.setStockCode(oldDeal.getStockCode());
				newDeal.setBuyOrSell(true);
				newDeal.setBuyQuantity(oldDeal.getBuyQuantity() - dealEntity.getSellQuantity());
				newDeal.setBuyPrice(oldDeal.getBuyPrice());
				newDeal.setBuyTime(oldDeal.getBuyTime());
				newDeal.setOwner(ownerEntity);
				this.dealService.add(newDeal);
				oldDeal.setBuyQuantity(dealEntity.getSellQuantity());
				oldDeal.setDealRoi(oldDeal.getSellPrice().multiply(new BigDecimal(oldDeal.getSellQuantity())).subtract(oldDeal.getBuyPrice().multiply(new BigDecimal(oldDeal.getBuyQuantity()))));
				this.dealService.add(oldDeal);
			}else if(oldDeal.getBuyQuantity() == dealEntity.getSellQuantity()){
				oldDeal.setDealRoi(oldDeal.getSellPrice().multiply(new BigDecimal(oldDeal.getSellQuantity())).subtract(oldDeal.getBuyPrice().multiply(new BigDecimal(oldDeal.getBuyQuantity()))));
				
				this.dealService.add(oldDeal);
			}else{
				oldDeal.setSellQuantity(oldDeal.getBuyQuantity());
				oldDeal.setDealRoi(oldDeal.getSellPrice().multiply(new BigDecimal(oldDeal.getSellQuantity())).subtract(oldDeal.getBuyPrice().multiply(new BigDecimal(oldDeal.getBuyQuantity()))));
				this.dealService.add(oldDeal);
			}
			
			return "redirect:/stock/recorder/" + oldDeal.getId();
		}
	}
	//修改deal
	@RequestMapping(value="/stock/deal/{id}/update",method=RequestMethod.GET)
	public String updatedealHandler(@PathVariable Integer id, Map<String,Object> modelMap,Model model){
		DealEntity dealEntity = new DealEntity();
		dealEntity = dealService.getDealById(id);
		modelMap.put("dealEntity", dealEntity);
		Boolean isUpdate = true;
		model.addAttribute("isUpdate", isUpdate);
		return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
	}
	
	//修改deal， 处理
	@RequestMapping(value= "/stock/deal/{id}/update",method=RequestMethod.POST)
	public String precessUpdateHandler(@PathVariable Integer id, @Valid DealEntity dealEntity, BindingResult result,Principal principal){
		if(result.hasErrors()){
			return VIEWS_DEAL_ADD_OR_UPDATE_FORM;
		}else{
			String username = principal.getName();
			OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
			DealEntity oldDeal = this.dealService.getDealById(id);
			oldDeal.setBuyOrSell(dealEntity.getBuyOrSell());
			oldDeal.setBuyPrice(dealEntity.getBuyPrice());
			oldDeal.setBuyQuantity(dealEntity.getBuyQuantity());
			oldDeal.setBuyTime(dealEntity.getBuyTime());
			if(! dealEntity.getBuyOrSell()){
				oldDeal.setSellPrice(dealEntity.getSellPrice());
				oldDeal.setSellQuantity(dealEntity.getSellQuantity());
				oldDeal.setSellTime(dealEntity.getSellTime());
				oldDeal.setDealRoi(oldDeal.getSellPrice().multiply(new BigDecimal(oldDeal.getSellQuantity())).subtract(oldDeal.getBuyPrice().multiply(new BigDecimal(oldDeal.getBuyQuantity()))));
			}
			this.dealService.add(oldDeal);
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
