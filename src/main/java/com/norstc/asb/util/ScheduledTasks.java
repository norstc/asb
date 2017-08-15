package com.norstc.asb.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.norstc.asb.stock.StockEntity;
import com.norstc.asb.stock.StockService;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	private StockService stockService ;
	
	
	@Autowired
	public void setStockService(StockService stockService){
		this.stockService = stockService;
	}
	
	//更新到数据库
	@Scheduled(fixedDelay = 15000)
	public void updateStockEntity(){
		BigDecimal stockCurrentPrice = new BigDecimal(100.0);
		BigDecimal stockAiRoi = new BigDecimal(0);
		BigDecimal stockAiPrice = new BigDecimal(0);
		List<StockEntity> listStockEntity = stockService.findAll();
		for (StockEntity oneStockEntity :listStockEntity){
			//log.info("stock code is {}", oneStockEntity.getStockCode());
			String stockCode = oneStockEntity.getStockCode();
			String quoteUrl="http://hq.sinajs.cn/list=sh" + stockCode;
			String result = null;
			try {
				result = doGetQuote(quoteUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stockCurrentPrice = new BigDecimal(result);
			stockAiPrice = oneStockEntity.getAiPrice();
			stockAiRoi = stockAiPrice.subtract(stockCurrentPrice);
			
			stockAiRoi = stockAiRoi.divide(stockCurrentPrice,3,RoundingMode.CEILING);
			oneStockEntity.setCurrentPrice(stockCurrentPrice);
			oneStockEntity.setAiRoi(stockAiRoi);
			stockService.add(oneStockEntity);
		}
	}
	
	//定时任务示例
	@Scheduled(initialDelay = 10000, fixedDelay=5000)
	public void reportCurrentTime(){
		//System.out.println("test for scheduled task");
		//log.info("The time is now {}",dateFormat.format(new Date()));
	}
	//cron = 秒 分 时 日 月 星期
	// "0 0 * * * *" = the top of every hour of every day.
    // "*/10 * * * * *" = every ten seconds.
    // "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    // "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
    // "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
    // "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    // "0 0 0 25 12 ?" = every Christmas Day at midnight

	//定时任务示例2：从sina获取股票价格
	@Scheduled(cron = "*/15 * 9-15 * * MON-FRI")
	public void getStockPrice(){
		//System.out.println("test for stock price");
		String stockCode="600000";
		String quoteUrl="http://hq.sinajs.cn/list=sh" + stockCode;
		String result = null;
		try {
			result = doGetQuote(quoteUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//log.info("the current price is {}",result );
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
			String ret = stringBuilder.toString();
			String[] aret = ret.split(",");
			//按 ‘，’ 拆分后，第4个元素是当前价格，第一元素中是证券名称，有乱码，待解决
			return aret[3];
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
