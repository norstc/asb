package com.norstc.asb.deal;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.norstc.asb.model.BaseEntity;

@Entity
@Table(name = "deals")
public class DealEntity extends BaseEntity{
	
	@Column(name = "stock_code")
	@NotNull
	private String stockCode;
	
	@Column(name = "deal_time")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date dealTime;
	
	@Column(name = "buy_or_sell")
	@NotNull
	private Boolean buyOrSell;
	
	@Column(name = "deal_price")
	@NotNull
	private BigDecimal dealPrice;
	
	@Column(name = "deal_quantity")
	@NotNull
	private Integer dealQuantity;
	
	@Column(name = "deal_roi")
	private BigDecimal dealRoi;

	
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Boolean getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(Boolean buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public BigDecimal getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}

	public BigDecimal getDealRoi() {
		return dealRoi;
	}

	public void setDealRoi(BigDecimal dealRoi) {
		this.dealRoi = dealRoi;
	}

	public Integer getDealQuantity() {
		return dealQuantity;
	}

	public void setDealQuantity(Integer dealQuantity) {
		this.dealQuantity = dealQuantity;
	}
	
	

}
