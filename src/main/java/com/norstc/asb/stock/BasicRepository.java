package com.norstc.asb.stock;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface BasicRepository extends Repository<BasicEntity, Integer> {
	List<BasicEntity> findAll();
	
	BasicEntity findById(Integer id);
	BasicEntity findByStockCode(String stockCode);
	
	void save(BasicEntity basicEntity);
	void delete(BasicEntity basicEntity);
	
	
	
}
