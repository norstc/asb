package com.norstc.asb.stock;

public interface BasicService {
	BasicEntity getBasicById(Integer id);
	BasicEntity getBasicByStockCode(String stockCode);
	
	void add(BasicEntity basicEntity);
	void deleteBasic(BasicEntity basicEntity);
}
