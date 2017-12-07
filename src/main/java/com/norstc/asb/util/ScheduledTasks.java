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
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.norstc.asb.stock.BasicEntity;
import com.norstc.asb.stock.BasicService;
import com.norstc.asb.stock.StockEntity;
import com.norstc.asb.stock.StockService;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private BasicService basicService;
	private StockService stockService ;
	
	
	@Autowired
	public void setStockService(StockService stockService){
		this.stockService = stockService;
	}
	
	@Autowired
	public void setBasicService(BasicService basicService){
		this.basicService = basicService;
	}
	
	//更新到基本信息库
	//定时任务,每隔5分钟执行一次
	//@Scheduled(cron = "0 */5 * * * *")
	//定时任务，固定间隔15秒执行一次，调试用
	//@Scheduled(fixedDelay = 15000)
	//定时任务，设定时间执行，调试用
	//@Scheduled(cron="0 45 1 * * *")
	//定时任务，每天晚上0点执行一次，生产环境
	@Scheduled(cron="0 0 0 * * *")
	public void updateBasicEntity(){
		BigDecimal stockDividend = new BigDecimal(0);
		List<BasicEntity> listBasicEntity = basicService.findAll();
		for (BasicEntity oneBasicEntity: listBasicEntity){
			String stockCode = oneBasicEntity.getStockCode();
			String quoteUrl = "http://vip.stock.finance.sina.com.cn/corp/go.php/vISSUE_ShareBonus/stockid/" + stockCode + ".phtml";
			String result = null;
			try {
				log.info("get dividend from :  " + quoteUrl);
				result = doGetDividend(quoteUrl);
				log.info("get dividend: " + result);
			}catch(Exception e){
				e.printStackTrace();
			}
			stockDividend = new BigDecimal(result);
			oneBasicEntity.setStockDividend(stockDividend);
			basicService.add(oneBasicEntity);
			//延迟10秒，否则会屏蔽
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//使用 Jsoup 获取分红
	private String doGetDividend(String quoteUrl) {
		//网页中分红的id
		String idShareBonus = "sharebonus_1";
		String currentYear = getCurrentYear();
		try{
			//直接打开网页，获取内容
			Document doc = Jsoup.connect(quoteUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
			if(doc != null ){
				//分红表格
				Element shareBonusTable = doc.getElementById(idShareBonus);
				//去除thead，只拿表格中的tbody
				Elements tbodys = shareBonusTable.getElementsByTag("tbody");
				for (Element tbody: tbodys){
					
					if(tbody.text().contains(currentYear)){
						//当期有分红
						Elements trs = tbody.getElementsByTag("tr");
						Double dividend = 0.0;
						for(Element tr: trs){
							//判断是否为当年的分红
							if(tr.text().contains(currentYear)){
									Elements tds = tr.getElementsByTag("td");
									//第一个td是时间，第4个td是分红数据
									//极少数当年会有两次分红
									dividend +=  Double.parseDouble(tds.get(3).text());
							}
						}
						return String.format("%.2f",dividend);
					}else{
						//当期没分红
						return "0";
					}
				}
				
				
			}else{
					return "0";
			}
			
		}catch (ConnectException ce){
			ce.printStackTrace();
			return "0";
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}finally{
			
		}
		return "0";
	}

	//获取当前年份
	private String getCurrentYear() {
		String ret = "";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month > 6){
			//上半年时看前年的分红
			 ret = Integer.toString(year);
		}else{
			//下半年时看今年的分红
			 ret = Integer.toString(year-1);
		}
		
		return ret;
	}

	private String getTbodyByHand(String bonusTable) {
		int iStartPos = bonusTable.indexOf("tbody");
		int iLastPos = bonusTable.lastIndexOf("tbody");
		log.info("tbody location is: " + iStartPos + "-" + iLastPos);
		String bonusBody = bonusTable.substring(iStartPos-1, iLastPos+6);
		log.info("bonusBody is: " + bonusBody);
		return bonusBody;
	}

	//更新到数据库
	//@Scheduled(fixedDelay = 15000)
	//9:00, 9:30, 10:00, 10:30 ~ 15:00 15:30 every monday to friday
	@Scheduled(cron = "0 0/30 9-15 * * MON-FRI")
	public void updateStockEntity(){
		BigDecimal stockCurrentPrice = new BigDecimal(100.0);
		BigDecimal stockAiRoi = new BigDecimal(0);
		BigDecimal stockAiPrice = new BigDecimal(0);
		List<StockEntity> listStockEntity = stockService.findAll();
		for (StockEntity oneStockEntity :listStockEntity){
			//log.info("stock code is {}", oneStockEntity.getStockCode());
			String stockCode = oneStockEntity.getStockCode();
			String stockMarket = getStockMarket(stockCode);
			String quoteUrl=stockMarket + stockCode;
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
	
	//根据证券代码识别是深圳还是上海
	//3 创业版 sz
	//0 深市 sz
	//6 沪市 sh
	private String getStockMarket(String stockCode) {
		if(stockCode == null) {
			log.info("stockCode is null");
			return null;
		}else{
			log.info("Stock Code is: " + stockCode);
		}
		String stockMarket = null;
		char firstDigit = stockCode.charAt(0);
		if(firstDigit == '\0'){
			log.info("firstDigit is null");
			return null;
		}else{
			log.info("firstDigit is : " + firstDigit);
		}
		switch (firstDigit) {
		case '6' : stockMarket="http://hq.sinajs.cn/list=sh";
		break;
		case '3' :
		case '0' : stockMarket="http://hq.sinajs.cn/list=sz";
		break;
		default : stockMarket="http://hq.sinajs.cn/list=sh";
		break;
		}
		return stockMarket;
	}

	//定时任务示例
	//@Scheduled(initialDelay = 10000, fixedDelay=5000)
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
	//@Scheduled(cron = "*/15 * 9-15 * * MON-FRI")
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

	//从字符串中获得报价
	private String doGetQuote(String quoteUrl) throws Exception {
		URL url = null;
		BufferedReader reader = null;
		StringBuilder stringBuilder;
		
		log.info("get " + quoteUrl);
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
			log.info("ret is :" + ret);
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
