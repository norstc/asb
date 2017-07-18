package com.norstc.asb.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Scheduled(fixedDelay=5000)
	public void reportCurrentTime(){
		System.out.println("test for scheduled task");
		log.info("The time is now {}",dateFormat.format(new Date()));
	}
	
	@Scheduled(fixedDelay=10000)
	public void getStockPrice(){
		System.out.println("test for stock price");
		//todo 
	}
}
