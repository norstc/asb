package com.norstc.asb.deal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.norstc.asb.owner.OwnerEntity;

@Service
public class DealServiceImpl implements DealService{

	private DealRepository dealRepository;
	
	@Autowired
	public void setDealRepository(DealRepository dealRepository){
		this.dealRepository = dealRepository;
	}
	
	@Override
	public DealEntity getDealById(Integer id) {
		
		return dealRepository.findById(id);
	}

	@Override
	public List<DealEntity> findAll() {
		
		return dealRepository.findAll();
	}

	@Override
	public void add(DealEntity dealEntity) {
		this.dealRepository.save(dealEntity);
		
	}

	@Override
	public void deleteDeal(DealEntity dealEntity) {
		this.dealRepository.delete(dealEntity);
		
	}

	@Override
	public List<DealEntity> findByOwner(OwnerEntity ownerEntity) {
		
		return dealRepository.findByOwner(ownerEntity);
	}

	@Override
	public List<DealEntity> findByOwnerAndIsBuy(OwnerEntity ownerEntity, boolean buyOrSell) {
		
		return dealRepository.findByOwnerAndIsBuy(ownerEntity, buyOrSell);
	}

}
