package com.norstc.asb.stock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StockLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private StockRepository stockRepository;
	
	private Logger log = Logger.getLogger(StockLoader.class);
	
	@Autowired
	public void setStockRepository(StockRepository stockRepository){
		this.stockRepository = stockRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		StockEntity se = new StockEntity();
		StockType stockType = new StockType();
		se.setId(1);
		se.setStockCode("600000");
		se.setStockName("浦发银行");
		se.setType(null);
		se.setCurrentPrice(10f);
		se.setAiPrice(12.2f);
		se.setAiRoi(0.2f);
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		se.setId(2);
		se.setStockCode("600004");
		se.setStockName("白云机场");
		se.setType(null);
		se.setCurrentPrice(1f);
		se.setAiPrice(2.2f);
		se.setAiRoi(0.1f);
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		se.setId(3);
		se.setStockCode("600005");
		se.setStockName("武汉钢铁");
		se.setType(null);
		se.setCurrentPrice(20f);
		se.setAiPrice(22.2f);
		se.setAiRoi(0.4f);
		se.setOwner(null);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
	}

}
