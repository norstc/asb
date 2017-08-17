package com.norstc.asb.deal;

import java.util.List;

import com.norstc.asb.owner.OwnerEntity;

public interface DealService {
	DealEntity getDealById(Integer id);
	List<DealEntity> findAll();
	void add(DealEntity dealEntity);
	void deleteDeal(DealEntity dealEntity);
	List<DealEntity> findByOwner(OwnerEntity ownerEntity);
	
	List<DealEntity> findByOwnerAndIsBuy(OwnerEntity ownerEntity, boolean buyOrSell);
}
