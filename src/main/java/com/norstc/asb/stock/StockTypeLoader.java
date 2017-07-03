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
		this.stockTypeRepository = stockTypeRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		StockType st = new StockType();
		st.setId(5);
		st.setName("great");
		
		stockTypeRepository.save(st);
		
		st.setId(6);
		st.setName("minor");
		
		stockTypeRepository.save(st);
		
		st.setId(7);
		st.setName("strategy");
		
		stockTypeRepository.save(st);
	}

}
