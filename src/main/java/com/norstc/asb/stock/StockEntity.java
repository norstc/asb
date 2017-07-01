package com.norstc.asb.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.norstc.asb.model.NamedEntity;
import com.norstc.asb.owner.OwnerEntity;


@Entity
@Table(name = "stocks")
public class StockEntity extends NamedEntity{

	@Column(name = "stock_code")
	private String stockCode;
	
	@Column(name = "stock_name")
	private String stockName;
	
	//use BigDecimal to present currency
	@Column(name = "current_price")
	private BigDecimal currentPrice;
	
	@Column(name = "ai_price")
	private BigDecimal aiPrice;
	
	@Column(name = "ai_roi")
	private BigDecimal aiRoi;
	
	
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private StockType type;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private OwnerEntity owner;
	
	
	
	
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}



	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}



	public BigDecimal getAiPrice() {
		return aiPrice;
	}



	public void setAiPrice(BigDecimal aiPrice) {
		this.aiPrice = aiPrice;
	}



	public BigDecimal getAiRoi() {
		return aiRoi;
	}



	public void setAiRoi(BigDecimal aiRoi) {
		this.aiRoi = aiRoi;
	}



	public String getStockCode() {
		return stockCode;
	}



	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}



	public String getStockName() {
		return stockName;
	}



	public void setStockName(String stockName) {
		this.stockName = stockName;
	}



	public StockType getType() {
		return type;
	}



	public void setType(StockType type) {
		this.type = type;
	}




	public OwnerEntity getOwner() {
		return owner;
	}



	public void setOwner(OwnerEntity ownerEntity) {
		this.owner =ownerEntity;
		
	}
	
	

}
