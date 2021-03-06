package com.norstc.asb.stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.norstc.asb.owner.OwnerEntity;

public interface StockRepository extends Repository<StockEntity, Integer>{
	@Query("SELECT name FROM StockType stockType ORDER BY name")
	@Transactional(readOnly = true)
	List<StockType> findStockTypes();
	
	
	@Transactional(readOnly = true)
	StockEntity findById(Integer id);
	
	void save(StockEntity stockEntity);
	
	List<StockEntity> findAll();
	
	
	void delete(StockEntity stockEntity);


	@Transactional(readOnly = true)
	List<StockEntity> findByOwner(OwnerEntity ownerEntity);
}
