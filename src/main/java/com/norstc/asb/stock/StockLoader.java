package com.norstc.asb.stock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.norstc.asb.deal.DealEntity;
import com.norstc.asb.deal.DealRepository;
import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerRepository;
import com.norstc.asb.owner.OwnerService;
import com.norstc.asb.owner.RoleEntity;
import com.norstc.asb.owner.RoleService;

@Component
public class StockLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private StockRepository stockRepository;
	private StockTypeRepository stockTypeRepository;
	private DealRepository dealRepository;
	private RoleService roleService;
	private OwnerService ownerService;
	
	
	private Logger log = Logger.getLogger(StockLoader.class);
	
	@Autowired
	public void setOwnerService(OwnerService ownerService){
		this.ownerService = ownerService;
	}
	@Autowired
	public void setRoleService(RoleService roleService){
		this.roleService = roleService;
	}
	@Autowired
	public void setDealRepository(DealRepository dealRepository){
		this.dealRepository = dealRepository;
	}
	
	@Autowired
	public void setStockRepository(StockRepository stockRepository){
		this.stockRepository = stockRepository;
	}
	
	@Autowired
	public void setStockTypeRepository(StockTypeRepository stockTypeRepository){
		this.stockTypeRepository = stockTypeRepository;
	}
	
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadStocks();
		loadStockTypes();
		loadOwners();
		loadRoles();
		loadDeals();
		assignUsersToUserRole();
		assignUsersToAdminRole();
	}

	private void assignUsersToAdminRole() {
		List<RoleEntity> roles = (List<RoleEntity>) roleService.listAll();
		List<OwnerEntity> owners = (List<OwnerEntity>) ownerService.listAll();
		roles.forEach(role -> {
			if(role.getRole().equalsIgnoreCase("ADMIN")){
				owners.forEach(o -> {
					if(o.getName().equals("admin")){
						o.addRole(role);
						ownerService.saveOrUpdate(o);
					}
				});
			}
		});
		
	}
	private void assignUsersToUserRole() {
		List<RoleEntity> roles = (List<RoleEntity>)roleService.listAll();
		List<OwnerEntity> owners = (List<OwnerEntity>)ownerService.listAll();
		roles.forEach(role ->{
			if(role.getRole().equalsIgnoreCase("USER")){
				owners.forEach(o -> {
					if(o.getName().equals("users")){
						o.addRole(role);
						ownerService.saveOrUpdate(o);
					}
				});
			}
		});
	}
	private void loadDeals() {
		DealEntity dealEntity = new DealEntity();
		//init dealRepository
		dealEntity.setId(1);
		dealEntity.setStockCode("600000");
		dealEntity.setBuyOrSell(true);
		dealEntity.setBuyPrice(new BigDecimal(12.2));
		dealEntity.setBuyQuantity(1000);
		dealEntity.setBuyTime(new Date("1980/9/9"));
		
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		dealEntity.setId(2);
		dealEntity.setStockCode("600000");
		dealEntity.setBuyOrSell(false);
		dealEntity.setBuyPrice(new BigDecimal(13.2));
		dealEntity.setBuyTime(new Date("1982/2/2"));
		dealEntity.setBuyQuantity(1000);
		dealEntity.setSellTime(new Date("1982/3/3"));
		dealEntity.setSellPrice(new BigDecimal(15.2));
		dealEntity.setSellQuantity(1000);
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(3);
		dealEntity.setStockCode("600001");
		dealEntity.setBuyOrSell(true);
		dealEntity.setBuyPrice(new BigDecimal(13.2));
		dealEntity.setBuyTime(new Date("2013/12/9"));
		dealEntity.setBuyQuantity(2000);
		dealEntity.setSellTime(null);
		dealEntity.setSellPrice(null);
		dealEntity.setSellQuantity(null);
		dealEntity.setDealRoi(null);
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(4);
		dealEntity.setStockCode("600001");
		dealEntity.setBuyOrSell(true);
		dealEntity.setBuyPrice(new BigDecimal(13.2));
		dealEntity.setBuyTime(new Date("2014/3/2"));
		dealEntity.setBuyQuantity(2000);
		dealEntity.setSellTime(null);
		dealEntity.setSellPrice(null);
		dealEntity.setSellQuantity(null);
		dealEntity.setDealRoi(null);
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(5);
		dealEntity.setStockCode("600002");
		dealEntity.setBuyOrSell(true);
		dealEntity.setBuyPrice(new BigDecimal(13.2));
		dealEntity.setBuyTime(new Date("2016/3/3"));
		dealEntity.setBuyQuantity(3000);
		dealEntity.setSellTime(null);
		dealEntity.setSellPrice(null);
		dealEntity.setSellQuantity(null);
		dealEntity.setDealRoi(null);
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		dealEntity.setId(6);
		dealEntity.setStockCode("600002");
		dealEntity.setBuyOrSell(true);
		dealEntity.setBuyPrice(new BigDecimal(13.2));
		dealEntity.setBuyQuantity(3000);
		dealEntity.setBuyTime(new Date("2017/4/4"));
		dealEntity.setSellTime(null);
		dealEntity.setSellPrice(null);
		dealEntity.setSellQuantity(null);
		dealEntity.setDealRoi(null);
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
	}

	private void loadRoles() {
		RoleEntity role = new RoleEntity();
		role.setRole("USER");
		roleService.saveOrUpdate(role);
		
		role.setRole("ADMIN");
		roleService.saveOrUpdate(role);
		
	}

	private void loadOwners() {
		OwnerEntity ownerEntity = new OwnerEntity();
		//initial owner
		ownerEntity.setId(1);
		ownerEntity.setName("tom");
		ownerEntity.setPassword("tom");
		ownerEntity.setFirstName("Tom");
		ownerEntity.setLastName("Hagens");
		ownerEntity.setAddress("shanghai xuhui");
		ownerEntity.setCity("Shanghai");
		ownerEntity.setTelephone("12345678");
		ownerService.saveOrUpdate(ownerEntity);
		
		ownerEntity.setId(2);
		ownerEntity.setName("michele");
		ownerEntity.setPassword("micchele");
		ownerEntity.setFirstName("Michele");
		ownerEntity.setLastName("Conleon");
		ownerEntity.setAddress("America");
		ownerEntity.setCity("Newyork");
		ownerEntity.setTelephone("11111111");
		ownerService.saveOrUpdate(ownerEntity);
		
		ownerEntity.setId(3);
		ownerEntity.setName("user");
		ownerEntity.setPassword("user");
		ownerEntity.setFirstName("u2");
		ownerEntity.setLastName("u3");
		ownerEntity.setAddress("America");
		ownerEntity.setCity("Newyork");
		ownerEntity.setTelephone("11111111");
		ownerService.saveOrUpdate(ownerEntity);
		
		ownerEntity.setId(4);
		ownerEntity.setName("admin");
		ownerEntity.setPassword("admin");
		ownerEntity.setFirstName("u4");
		ownerEntity.setLastName("u5");
		ownerEntity.setAddress("America");
		ownerEntity.setCity("Newyork");
		ownerEntity.setTelephone("11111111");
		ownerService.saveOrUpdate(ownerEntity);
		
		
				
	}

	private void loadStockTypes() {
		StockType stockType = new StockType();
		//initial stock type 
		stockType.setId(1);
		stockType.setName("clever");
		stockTypeRepository.save(stockType);
		stockType.setId(2);
		stockType.setName("master");
		stockTypeRepository.save(stockType);
		stockType.setId(3);
		stockType.setName("golden");
		stockTypeRepository.save(stockType);
		stockType.setId(4);
		stockType.setName("diamand");
		stockTypeRepository.save(stockType);
		
	}

	private void loadStocks() {
		StockEntity se = new StockEntity();
		StockType stockType = new StockType();
		OwnerEntity ownerEntity = new OwnerEntity();
		
		stockType =	stockTypeRepository.findOne(1);
		if(stockType == null){
			log.info("stockType is null");
		}else{
			log.info("stockType : "+ stockType.getId());
		}
		ownerEntity = ownerService.getById(1);
		//initial stock 
		se.setId(1);
		se.setStockCode("600000");
		se.setStockName("浦发银行");
		se.setType(stockType);
		se.setCurrentPrice(new BigDecimal("10.2"));
		se.setAiPrice(new BigDecimal("12.2"));
		se.setAiRoi(new BigDecimal("0.2"));
		se.setOwner(ownerEntity);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		
		stockType = stockTypeRepository.findOne(2);
		ownerEntity = ownerService.getById(2);
		se.setId(2);
		se.setStockCode("600004");
		se.setStockName("白云机场");
		se.setType(stockType);
		se.setCurrentPrice(new BigDecimal(1.2));
		se.setAiPrice(new BigDecimal("1.5"));
		se.setAiRoi(new BigDecimal("0.1"));
		se.setOwner(ownerEntity);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		stockType = stockTypeRepository.findOne(3);
		ownerEntity = ownerService.getById(2);
		se.setId(3);
		se.setStockCode("600005");
		se.setStockName("武汉钢铁");
		se.setType(stockType);
		se.setCurrentPrice(new BigDecimal(20.2));
		se.setAiPrice(new BigDecimal("22.3"));
		se.setAiRoi(new BigDecimal("0.4"));
		se.setOwner(ownerEntity);
		
		stockRepository.save(se);
		
		log.info("saved stockentity: "+ se.getId());
		
		
		
	}

}
