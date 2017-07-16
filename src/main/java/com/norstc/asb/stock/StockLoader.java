package com.norstc.asb.stock;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.norstc.asb.deal.DealEntity;
import com.norstc.asb.deal.DealRepository;
import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerRepository;

@Component
public class StockLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private StockRepository stockRepository;
	private StockTypeRepository stockTypeRepository;
	private OwnerRepository ownerRepository;
	private DealRepository dealRepository;
	
	private Logger log = Logger.getLogger(StockLoader.class);
	
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
	
	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository){
		this.ownerRepository  = ownerRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		StockEntity se = new StockEntity();
		StockType stockType = new StockType();
		OwnerEntity ownerEntity = new OwnerEntity();
		DealEntity dealEntity = new DealEntity();
		
		//initial owner
		ownerEntity.setId(1);
		ownerEntity.setFirstName("Tom");
		ownerEntity.setLastName("Hagens");
		ownerEntity.setAddress("shanghai xuhui");
		ownerEntity.setCity("Shanghai");
		ownerEntity.setTelephone("12345678");
		ownerRepository.save(ownerEntity);
		
		ownerEntity.setId(2);
		ownerEntity.setFirstName("Michele");
		ownerEntity.setLastName("Conleon");
		ownerEntity.setAddress("America");
		ownerEntity.setCity("Newyork");
		ownerEntity.setTelephone("11111111");
		ownerRepository.save(ownerEntity);
		
		
		
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
		
		
		stockType =	stockTypeRepository.findOne(1);
		if(stockType == null){
			log.info("stockType is null");
		}else{
			log.info("stockType : "+ stockType.getId());
		}
		ownerEntity = ownerRepository.findOne(1);
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
		ownerEntity = ownerRepository.findOne(2);
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
		ownerEntity = ownerRepository.findOne(2);
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
		
		//init dealRepository
		dealEntity.setId(1);
		dealEntity.setStockCode("600000");
		dealEntity.setBuyOrSell(true);
		dealEntity.setDealPrice(new BigDecimal(12.2));
		dealEntity.setDealTime(new Date("1980/9/9"));
		
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		dealEntity.setId(2);
		dealEntity.setStockCode("600000");
		dealEntity.setBuyOrSell(false);
		dealEntity.setDealPrice(new BigDecimal(13.2));
		dealEntity.setDealTime(new Date("1980/2/2"));
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(3);
		dealEntity.setStockCode("600001");
		dealEntity.setBuyOrSell(true);
		dealEntity.setDealPrice(new BigDecimal(13.2));
		dealEntity.setDealTime(new Date("1980/12/9"));
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(4);
		dealEntity.setStockCode("600001");
		dealEntity.setBuyOrSell(false);
		dealEntity.setDealPrice(new BigDecimal(13.2));
		dealEntity.setDealTime(new Date("2014/3/2"));
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		
		dealEntity.setId(5);
		dealEntity.setStockCode("600002");
		dealEntity.setBuyOrSell(true);
		dealEntity.setDealPrice(new BigDecimal(13.2));
		dealEntity.setDealTime(new Date("2016/3/3"));
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
		dealEntity.setId(6);
		dealEntity.setStockCode("600002");
		dealEntity.setBuyOrSell(false);
		dealEntity.setDealPrice(new BigDecimal(13.2));
		dealEntity.setDealTime(new Date("2014/4/4"));
		dealEntity.setDealRoi(new BigDecimal(0.2));
		
		dealRepository.save(dealEntity);
		log.info("saved dealEntity: " + dealEntity.getId());
		
	}

}
