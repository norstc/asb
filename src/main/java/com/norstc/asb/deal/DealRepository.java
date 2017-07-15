package com.norstc.asb.deal;

import java.util.List;


import org.springframework.data.repository.Repository;

public interface DealRepository extends Repository<DealEntity, Integer>{
	List<DealEntity> findAll();
	
	DealEntity findById(Integer id);
	
	void save(DealEntity dealEntity);
	
	void delete(DealEntity dealEntity);
	
	

}
