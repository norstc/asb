package com.norstc.asb.stock;

import java.util.List;

import com.norstc.asb.owner.OwnerEntity;

public interface StockService {
	StockEntity getStockById(Integer id);
	List<StockEntity> findAll();
	void add(StockEntity stockEntity);
	void deleteStock(StockEntity stockEntity);
	
	List<StockEntity> findByOwner(OwnerEntity ownerEntity);
}
