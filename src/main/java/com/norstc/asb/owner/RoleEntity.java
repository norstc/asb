package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.norstc.asb.model.NamedEntity;

@Entity
@Table(name = "roles")
public class RoleEntity extends NamedEntity{
	
	private String role;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<OwnerEntity> owners = new ArrayList<>();
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	public List<OwnerEntity> getOwners(){
		return this.owners;
	}
	
	public void setOwners(List<OwnerEntity> owners){
		this.owners = owners;
	}
	
	public void addOwner(OwnerEntity ownerEntity){
		if(!this.owners.contains(ownerEntity)){
			this.owners.add(ownerEntity);
		}
		
		if(!ownerEntity.getRoles().contains(this)){
			ownerEntity.getRoles().add(this);
		}
	}
	
	public void removeOwner(OwnerEntity ownerEntity){
		this.owners.remove(ownerEntity);
		ownerEntity.getRoles().remove(this);
	}
}
