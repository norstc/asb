package com.norstc.asb.owner;

import java.util.List;

public interface OwnerService {
	List<OwnerEntity> listAll();
	OwnerEntity getById(Integer id);
	OwnerEntity saveOrUpdate(OwnerEntity ownerEntity);
	void delete(Integer id);
	
	OwnerEntity findByUsername(String username);
}
