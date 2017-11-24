package com.norstc.asb.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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

import com.norstc.asb.deal.DealEntity;
import com.norstc.asb.deal.DealService;
import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerService;
import com.norstc.asb.stock.BasicEntity;
import com.norstc.asb.stock.BasicService;
import com.norstc.asb.stock.StockEntity;
import com.norstc.asb.stock.StockService;

@Controller
public class StockController {
	private Logger log = Logger.getLogger(StockController.class);
	
	private static final String VIEWS_TARGET_ADD_OR_UPDATE_FORM = "stock/addOrUpdateTargetForm";
	private StockService stockService;
	private OwnerService ownerService;
	private DealService dealService;
	private BasicService basicService;
	
	@Autowired
	public void setBasicService(BasicService basicService){
		this.basicService = basicService;
	}
	
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
		
		return "stock/target";
	}
	
	@RequestMapping("/stock/target/{id}")
	public String mainTargetHandler(@PathVariable Integer id, Model model){
		StockEntity stockEntity = stockService.getStockById(id);
		String stockCode = stockEntity.getStockCode();
		//基本信息库
		
		BasicEntity basicEntity =	basicService.getBasicByStockCode(stockCode);
		if(basicEntity == null){
			basicEntity = new BasicEntity();
			basicEntity.setStockDividend(new BigDecimal(0));
			basicEntity.setStockAdvantage("--");
			basicEntity.setStockRisk("--");
		}
		model.addAttribute("stock",stockEntity);
		model.addAttribute("basic",basicEntity);
		return "stock/target";
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
			
			//检查是否是重复的stock
			StockEntity oldStockEntity = new StockEntity();
			List<StockEntity> ownerStocks = new ArrayList<StockEntity>();
			String ownerName = principal.getName();
			OwnerEntity ownerEntity = this.ownerService.findByUsername(ownerName);
			ownerStocks = this.stockService.findByOwner(ownerEntity);
			
			//检查目标总数是否超过7*level
			if(ownerStocks.size() >= 7*ownerEntity.getOwnerLevel()){
				result.rejectValue("stockCode", "error.stockEntity", "已经超出用户当前可以添加的目标数量" +7*ownerEntity.getOwnerLevel() );
				log.info("stock quantity is up to owner level limit" + 7*ownerEntity.getOwnerLevel());
				return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
			}
			boolean inStocks = false;
			for (StockEntity se: ownerStocks){
				if(se.getStockCode().equals(stockEntity.getStockCode())){
					inStocks = true;
				}
			}
			if (inStocks){
				//已经添加过了
				log.info("stock already added: " + stockEntity.getStockCode());
				result.rejectValue("stockCode", "error.stockEntity","stock already exist");
				return VIEWS_TARGET_ADD_OR_UPDATE_FORM;
			}else{
				this.stockService.add(stockEntity);
				log.info("processAdd ok: , stockEntity is: " + stockEntity.getStockCode());
				return "redirect:/stock/target/"+stockEntity.getId();
			}
		
			

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
			oldStock.setStockCode(stockEntity.getStockCode());
			oldStock.setStockName(stockEntity.getStockName());
			oldStock.setCurrentPrice(stockEntity.getCurrentPrice());
			oldStock.setAiPrice(stockEntity.getAiPrice());
			//更新目标价格后立即计算出当前预期收益率
			BigDecimal roi = stockEntity.getAiPrice().subtract(stockEntity.getCurrentPrice());
			log.info("roi is " + roi);
			log.info("stockEntity.getCurrentPrice() is " + stockEntity.getCurrentPrice());
			oldStock.setAiRoi(roi.divide(stockEntity.getCurrentPrice(),2));
			this.stockService.add(oldStock);
			return "redirect:/stock/target/" + oldStock.getId();

		}
	}
	
	
	//删除target
	@RequestMapping(value="/stock/target/{id}/delete",method=RequestMethod.GET)
	public String deleteTargetHandler(@PathVariable Integer id){
		StockEntity stockEntity = stockService.getStockById(id);
		stockService.deleteStock(stockEntity);
		return "redirect:/stock/target/";

	}
	
	//余额
	@RequestMapping("/stock/balance")
	public String mainBalanceHandler(Model model, Principal principal){
		String username = principal.getName();
		OwnerEntity ownerEntity = this.ownerService.findByUsername(username);
		List<DealEntity> deals = this.dealService.findByOwnerAndIsBuy(ownerEntity,true);
		model.addAttribute("owner", ownerEntity);
		model.addAttribute("deals",deals);
		return "stock/balance";
	}
	
	@RequestMapping("/stock/another")
	public String mainAnotherHandler(){
		return "stock/another";
	}
	
	@RequestMapping("/stock/secret")
	public String mainSecretHandler(){
		return "stock/secret";
	}
	

}
