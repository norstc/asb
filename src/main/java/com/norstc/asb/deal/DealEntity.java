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
	

	@Column(name = "buy_or_sell")
	@NotNull
	private Boolean buyOrSell;
	
	
	@Column(name = "buy_time")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date buyTime;

	@Column(name = "buy_price")
	@NotNull
	private BigDecimal buyPrice;
	
	@Column(name = "buy_quantity")
	@NotNull
	private Integer buyQuantity;
	
	@Column(name = "sell_time")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date sellTime;
	
	
	@Column(name = "sell_price")
	private BigDecimal sellPrice;
	
	@Column(name = "sell_quantity")
	private Integer sellQuantity;
	
	@Column(name = "deal_roi")
	private BigDecimal dealRoi;

	
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}


	public Boolean getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(Boolean buyOrSell) {
		this.buyOrSell = buyOrSell;
	}



	public BigDecimal getDealRoi() {
		return dealRoi;
	}

	public void setDealRoi(BigDecimal dealRoi) {
		this.dealRoi = dealRoi;
	}



	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Date getSellTime() {
		return sellTime;
	}

	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Integer sellQuantity) {
		this.sellQuantity = sellQuantity;
	}
	
	

}
