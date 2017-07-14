package com.norstc.asb.stock;

import java.util.List;

public interface StockService {
	StockEntity getStockById(Integer id);
	List<StockEntity> findAll();
	void add(StockEntity stockEntity);
	void deleteStock(StockEntity stockEntity);
}
