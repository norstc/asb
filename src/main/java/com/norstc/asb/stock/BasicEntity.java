package com.norstc.asb.stock;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.norstc.asb.model.NamedEntity;

@Entity
@Table(name="stock_basic")
public class BasicEntity extends NamedEntity{
	
	@Column(name="stock_code")
	@NotNull
	private String stockCode;
	
	@Column(name="stock_name")
	@NotNull
	private String stockName;
	
	@Column(name="stock_market")
	private String stockMarket;
	
	@Column(name="stock_dividend")
	private BigDecimal stockDividend;
	
	@Column(columnDefinition="TEXT", name="stock_advantage")
	private String stockAdvantage;
	
	
	@Column(columnDefinition="TEXT", name="stock_risk")
	private String stockRisk;


	
	
	public String getStockMarket() {
		return stockMarket;
	}


	public void setStockMarket(String stockMarket) {
		this.stockMarket = stockMarket;
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


	public BigDecimal getStockDividend() {
		return stockDividend;
	}


	public void setStockDividend(BigDecimal stockDividend) {
		this.stockDividend = stockDividend;
	}


	public String getStockAdvantage() {
		return stockAdvantage;
	}


	public void setStockAdvantage(String stockAdvantage) {
		this.stockAdvantage = stockAdvantage;
	}


	public String getStockRisk() {
		return stockRisk;
	}


	public void setStockRisk(String stockRisk) {
		this.stockRisk = stockRisk;
	}
	
	
	
}
