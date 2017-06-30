package com.norstc.asb.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StockTypeLoader implements ApplicationListener<ContextRefreshedEvent>{
	private StockTypeRepository stockTypeRepository;
	
	@Autowired
	public void setStockTypeRepository(StockTypeRepository stockTypeRepository){
		
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
