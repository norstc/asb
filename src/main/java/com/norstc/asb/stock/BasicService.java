package com.norstc.asb.stock;

import java.util.List;

public interface BasicService {
	BasicEntity getBasicById(Integer id);
	BasicEntity getBasicByStockCode(String stockCode);
	
	void add(BasicEntity basicEntity);
	void deleteBasic(BasicEntity basicEntity);
	
	List<BasicEntity> findAll();
}
