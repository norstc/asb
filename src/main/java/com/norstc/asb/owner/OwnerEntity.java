package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import com.norstc.asb.deal.DealEntity;
import com.norstc.asb.model.PersonEntity;
import com.norstc.asb.stock.StockEntity;

@Entity
@Table(name = "owners")
public class OwnerEntity extends PersonEntity{
	
	@Column(name = "address")
	@NotEmpty
	private String address;
	
	@Column(name = "city")
	@NotEmpty
	private String city;
	

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;
	
	//交易目标
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<StockEntity> stocks;

	//交易记录
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<DealEntity> deals;
	
	//帐号明细
	//todo
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<StockEntity> getStocks() {
		return stocks;
	}

	public void setStocks(Set<StockEntity> stocks) {
		this.stocks = stocks;
	}
	
	protected Set<StockEntity> getStocksInternal(){
		if (this.stocks == null){
			this.stocks = new HashSet<>();
		}
		return this.stocks;
	}
	
	protected void setStocksInternal(Set<StockEntity> stocks){
		this.stocks = stocks;
		
	}
	
	public List<StockEntity> getStocksList(){
		List<StockEntity> sortedStocks = new ArrayList<>(getStocksInternal());
		PropertyComparator.sort(sortedStocks, new MutableSortDefinition("name",true,true));
		return Collections.unmodifiableList(sortedStocks);
		
	}
	
	public void addStock(StockEntity stockEntity){
		if(stockEntity.isNew()){
			getStocksInternal().add(stockEntity);
		}
		stockEntity.setOwner(this);
	}
	
	/**
	 *按照名称返回股票，如果没有发现则返回空
	 */
	public StockEntity getStockEntity(String name){
		return getStockEntity(name,false);
	}

	public StockEntity getStockEntity(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for(StockEntity stockEntity:getStocksInternal()){
			if(!ignoreNew || !stockEntity.isNew()){
				String compName = stockEntity.getName();
				compName = compName.toLowerCase();
				if(compName.equals(name)){
					return stockEntity;
				}
			}
		}
		return null;
	}
	
	
	
	public Set<DealEntity> getDeals() {
		return deals;
	}

	public void setDeals(Set<DealEntity> deals) {
		this.deals = deals;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("new",this.isNew())
				.append("lastName",this.getLastName())
				.append("firstName", this.getFirstName())
				.append("address", this.address)
				.append("city", this.city)
				.append("telephone", this.telephone)
				.toString();
	}
}
