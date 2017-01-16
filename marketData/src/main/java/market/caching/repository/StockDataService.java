package market.caching.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import market.data.download.StockDownLoad;
import market.data.download.YahooDownloader;
import market.data.model.Stock;
import market.data.model.Symbol;
import market.data.util.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


@Service
public class StockDataService {

	@Value("${batch.size:100}")
	int batchSize;
	
	@Autowired
	private StockDownLoad stockDownLoad;
	
	 @Autowired
	 private CacheManager cacheManager;
	
	private static Logger log = Logger.getLogger(StockDataService.class);
	
	private ExecutorService executor = Executors.newFixedThreadPool(4);

	@PostConstruct
	public void init() {
		log.info("load stocks");
		loadStocksWithRx();
	}
	
	public void loadStocksWithRx() {
		Cache cache = cacheManager.getCache("stockCache");

		List<String> symbols = remoteSymbols();
		List<List<String>> subLists = Utils.sublist(symbols, batchSize);
		
		Observable<List<String>> symbs = Observable.from(subLists)
										.onErrorResumeNext(readFromCsv());
		
		YahooDownloader downloader = new YahooDownloader();

		symbs.
		subscribeOn(Schedulers.from(executor)).
		subscribe(syms-> {
			try {
				log.info("request symbols from yahoo:"+syms);
				List<Stock> stocks = downloader.doYahooDownload(syms);
				Observable.from(stocks)
				.subscribe((Stock stock)-> {
					cache.put(stock.getSymbol(), stock);
				});
				log.info("added symbols to cache:"+syms);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void loadStocks() {
		List<String> symbols = remoteSymbols();
		List<List<String>> subLists = Utils.sublist(symbols, 100);
		
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
	
	private Observable<List<String>> readFromCsv() {
		try {
			List<String> symbols = Files.readAllLines(Paths.get("data/symbols.txt"));
			return Observable.from(Utils.sublist(symbols, batchSize));
		} catch (IOException e) {
			log.error("error occured",e);
		}
		return null;
	}

	private List<String> remoteSymbols() {
		List<String> symbols = stockDownLoad.remoteSymbols();
		return symbols;
	}
}
