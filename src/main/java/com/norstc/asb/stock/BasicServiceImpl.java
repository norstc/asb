package com.norstc.asb.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicServiceImpl implements BasicService {

	private BasicRepository basicRepository;
	
	@Autowired
	public void setBasicRepository(BasicRepository basicRepository){
		this.basicRepository = basicRepository;
	}
	@Override
	public BasicEntity getBasicById(Integer id) {
		
		return basicRepository.findById(id);
	}

	@Override
	public BasicEntity getBasicByStockCode(String stockCode) {
		
		return basicRepository.findByStockCode(stockCode);
	}

	@Override
	public void add(BasicEntity basicEntity) {
		basicRepository.save(basicEntity);
		
	}

	@Override
	public void deleteBasic(BasicEntity basicEntity) {
		basicRepository.delete(basicEntity);
		
	}

}
