package market.caching.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import market.data.download.StockDownLoad;
import market.data.download.YahooDownloader;
import market.data.model.Stock;
import market.data.util.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class StockDataService {

	@Autowired
	private StockDownLoad stockDownLoad;
	
	 @Autowired
	 private CacheManager cacheManager;
	
	private static Logger log = Logger.getLogger(StockDataService.class);

	@PostConstruct
	public void init() {
		log.info("load stocks");
		process();
	}
	
	public void process() {
		List<String> symbols = stockDownLoad.remoteSymbols();
		List<List<String>> subLists = Utils.sublist(symbols, 10);
		
		YahooDownloader downloader = new YahooDownloader();
		
		Cache cache = cacheManager.getCache("stockCache");
		subLists.stream().parallel()
		//.limit(1)
		.forEach(syms-> {
			try {
				List<Stock> stocks = downloader.doYahooDownload(syms);
				stocks.forEach((Stock stock)->	cache.put(stock.getSymbol(), stock));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@Cacheable(value = "stockCache",key = "#symbol")
	public Stock getStockBySymbol(String symbol) {
	//this will not execute
		Cache cache = cacheManager.getCache("stockCache");
		return (Stock)cache.get(symbol);
		//return null;
	}
}
