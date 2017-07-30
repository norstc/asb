package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Service
@Profile("springdatajpa")
public class OwnerServiceImpl implements OwnerService {
	private OwnerRepository ownerRepository;
	
	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository){
		this.ownerRepository = ownerRepository;
	}

	private EncryptionService encryptionService;
	
	@Autowired
	public void setEncryptionService(EncryptionService encryptionService){
		this.encryptionService = encryptionService;
	}
	
	
	@Override
	public List<OwnerEntity> listAll() {
		List<OwnerEntity> owners = new ArrayList<>();
		ownerRepository.findAll().forEach(owners::add);
		return owners;
	}

	@Override
	public OwnerEntity getById(Integer id) {
		
		return ownerRepository.findOne(id);
	}

	@Override
	public OwnerEntity saveOrUpdate(OwnerEntity ownerEntity) {
		if(ownerEntity.getPassword() != null){
			ownerEntity.setEncryptedPassword(encryptionService.encryptString(ownerEntity.getPassword()));
		}
		return ownerRepository.save(ownerEntity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		ownerRepository.delete(id);
		
	}

	@Override
	public OwnerEntity findByName(String name) {
	
		return ownerRepository.findByName(name);
	}

}
