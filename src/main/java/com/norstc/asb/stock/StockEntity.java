package com.norstc.asb.stock;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.norstc.asb.model.NamedEntity;
import com.norstc.asb.owner.OwnerEntity;

@Entity
@Table(name = "stocks")
public class StockEntity extends NamedEntity{

	public void setOwner(OwnerEntity ownerEntity) {
		// TODO Auto-generated method stub
		
	}
	
	

}
