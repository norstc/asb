package com.norstc.asb.deal;

import java.util.List;

public interface DealService {
	DealEntity getDealById(Integer id);
	List<DealEntity> findAll();
	void add(DealEntity dealEntity);
	void deleteDeal(DealEntity dealEntity);
}
