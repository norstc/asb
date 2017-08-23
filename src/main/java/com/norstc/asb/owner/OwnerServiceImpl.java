package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Service
@Profile("mysql")
public class OwnerServiceImpl implements OwnerService {
	private OwnerRepository ownerRepository;
	private Logger log = Logger.getLogger(OwnerServiceImpl.class);
	
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
	public OwnerEntity findByUsername(String username) {
		log.info("ownerService find by username called on: " + username);
		return ownerRepository.findByUsername(username);
	}

}
