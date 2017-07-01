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
		st.setId(1);
		st.setName("diamond");
		
		stockTypeRepository.save(st);
		
		st.setId(2);
		st.setName("golden");
		
		stockTypeRepository.save(st);
		
		st.setId(3);
		st.setName("classic");
		
		stockTypeRepository.save(st);
	}

}
