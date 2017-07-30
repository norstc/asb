package com.norstc.asb.owner;

import java.util.List;

public interface RoleService {
	List<RoleEntity> listAll();
	RoleEntity getById(Integer id);
	RoleEntity saveOrUpdate(RoleEntity roleEntity);
	void delete(Integer id);
	
	
}
