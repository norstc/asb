package com.norstc.asb.owner;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer>{
	OwnerEntity findByName(String name);
}
