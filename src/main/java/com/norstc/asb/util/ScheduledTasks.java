package com.norstc.asb.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
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
		String stockCode="600000";
		String quoteUrl="http://hq.sinajs.cn/list=sh" + stockCode;
		String result = null;
		try {
			result = doGetQuote(quoteUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("the quote is {}",result );
	}

	private String doGetQuote(String quoteUrl) throws Exception {
		URL url = null;
		BufferedReader reader = null;
		StringBuilder stringBuilder;
		
		try{
			url = new URL(quoteUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setReadTimeout(15000);
			connection.connect();
			
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			stringBuilder = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}
			return stringBuilder.toString();
		} catch (ConnectException ce){
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
		}

	}
}
