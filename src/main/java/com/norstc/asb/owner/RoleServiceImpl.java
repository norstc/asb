package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mysql")
public class RoleServiceImpl  implements RoleService{
	private RoleRepository roleRepository;
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository){
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleEntity> listAll() {
		List<RoleEntity> roles = new ArrayList<>();
		roleRepository.findAll().forEach(roles::add);
		
		return roles;
	}

	@Override
	public RoleEntity getById(Integer id) {
		
		return roleRepository.findOne(id);
	}

	@Override
	public RoleEntity saveOrUpdate(RoleEntity roleEntity) {
		
		return roleRepository.save(roleEntity);
	}

	@Override
	public void delete(Integer id) {
		roleRepository.delete(id);
		
	}

}
