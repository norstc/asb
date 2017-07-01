package com.norstc.asb.stock;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StockLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private StockRepository stockRepository;
	private StockTypeRepository stockTypeRepository;
	
	private Logger log = Logger.getLogger(StockLoader.class);
	
	@Autowired
	public void setStockRepository(StockRepository stockRepository){
		this.stockRepository = stockRepository;
	}
	
	@Autowired
	public void setStockTypeRepository(StockTypeRepository stockTypeRepository){
		this.stockTypeRepository = stockTypeRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		StockEntity se = new StockEntity();
		StockType stockType = stockTypeRepository.findOne(1);
		
		se.setId(1);
		se.setStockCode("600000");
		se.setStockName("浦发银行");
		se.setType(stockType);
		se.setCurrentPrice(new BigDecimal("10.2"));
		se.setAiPrice(new BigDecimal("12.2"));
		se.setAiRoi(new BigDecimal("0.2"));
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		se.setId(2);
		se.setStockCode("600004");
		se.setStockName("白云机场");
		se.setType(null);
		se.setCurrentPrice(new BigDecimal(1.2));
		se.setAiPrice(new BigDecimal("1.5"));
		se.setAiRoi(new BigDecimal("0.1"));
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		se.setId(3);
		se.setStockCode("600005");
		se.setStockName("武汉钢铁");
		se.setType(null);
		se.setCurrentPrice(new BigDecimal(20.2));
		se.setAiPrice(new BigDecimal("22.3"));
		se.setAiRoi(new BigDecimal("0.4"));
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
	}

}
