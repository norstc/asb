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
		se.setId(1);
		se.setStockCode("600000");
		se.setStockName("浦发银行");
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		
	}

}
