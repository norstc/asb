package com.norstc.asb.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{
	private StockRepository stockRepository;
	
	@Autowired
	public void setStockRepository(StockRepository stockRepository){
		this.stockRepository = stockRepository;
	}
	
	@Override
	public StockEntity getStockById(Integer id){
		return stockRepository.findById(id);
	}

}
