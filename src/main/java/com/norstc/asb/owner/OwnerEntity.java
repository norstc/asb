package com.norstc.asb.owner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

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
	@Column(name = "username")
	@NotEmpty
	private String username;
	
	//不保存到数据库
	@Transient
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@Column(name = "encrypted_password")
	private String encryptedPassword;
	
	@Column(name ="enabled")
	private Boolean enabled = true;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<RoleEntity> roles = new ArrayList<>();
	
	
	@Column(name="failed_login_attempts")
	private Integer failedLoginAttempts = 0;
	
	
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 11)
	private String telephone;
	
	@Column(name ="owner_level")
	private Integer ownerLevel;
	
	
	//交易目标
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<StockEntity> stocks;

	//交易记录
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<DealEntity> deals;
	
	//帐户明细
	@Column(name="cash_start")
	@NotNull
	private BigDecimal cashStart;
	
	@Column(name="cash_left")
	private BigDecimal cashLeft;
	
	@Column(name="market_left")
	private BigDecimal marketLeft;
	
	@Column(name="cash_profit")
	private BigDecimal cashProfit;
	
	//login
	
	
	public BigDecimal getCashProfit() {
		return cashProfit;
	}

	public void setCashProfit(BigDecimal cashProfit) {
		this.cashProfit = cashProfit;
	}
	
	public BigDecimal getCashStart() {
		return cashStart;
	}

	public void setCashStart(BigDecimal cashStart) {
		this.cashStart = cashStart;
	}

	public BigDecimal getCashLeft() {
		return cashLeft;
	}

	public void setCashLeft(BigDecimal cashLeft) {
		this.cashLeft = cashLeft;
	}

	public BigDecimal getMarketLeft() {
		return marketLeft;
	}

	public void setMarketLeft(BigDecimal marketLeft) {
		this.marketLeft = marketLeft;
	}

	public Integer getOwnerLevel() {
		return ownerLevel;
	}

	public void setOwnerLevel(Integer ownerLevel) {
		this.ownerLevel = ownerLevel;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public void addRole(RoleEntity roleEntity){
		if(!this.roles.contains(roleEntity)){
			this.roles.add(roleEntity);
		}
		
		if(!roleEntity.getOwners().contains(this)){
			roleEntity.getOwners().add(this);
		}
	}
	
	public void removeRole(RoleEntity roleEntity){
		this.roles.remove(roleEntity);
		roleEntity.getOwners().remove(this);
	}
	
	
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
