package market.caching.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hazelcast.core.IMap;

import market.data.download.IExtradingMktData;
import market.data.download.StockDownLoad;
import market.data.model.Quote;
import market.data.model.Sector;
import market.data.util.Utils;
import rx.Observable;
import rx.schedulers.Schedulers;


@Service
public class StockDataService {

	@Value("${batch.size:100}")
	int batchSize;
	
	@Autowired
	private StockDownLoad stockDownLoad;
	
	@Autowired
	private IExtradingMktData iExtradingMktData;
	
	 @Autowired
	 private CacheManager cacheManager;
	
	 private static final Logger log = LoggerFactory
	            .getLogger(StockDataService.class);
	 
	private ExecutorService executor = Executors.newFixedThreadPool(4);

	@PostConstruct
	public void init() {
		log.info("load stocks");
		loadStocks();
		//loadStocksWithRx();
	}
	
	public void loadStocksWithRx() {
		Cache cache = cacheManager.getCache("stockCache");

		List<String> symbols = remoteSymbols();
		List<List<String>> subLists = Utils.sublist(symbols, batchSize);
		
		Observable<List<String>> symbs = readRemoteSymbols(subLists)
										.doOnError(s->log.error("error occured while getting symbols, falling back to read from csv:"+s))
										.onExceptionResumeNext(readFromCsv());
		symbs.
		subscribeOn(Schedulers.from(executor)).
		subscribe(syms-> {
			try {
				log.info("request symbols from yahoo:"+syms);
				List<Quote> quotes =  iExtradingMktData.quotes(syms);
				quotes.forEach((Quote quote)->	cache.put(quote.getSymbol(), quote));
				
				Observable.from(quotes)
				.subscribe((Quote quote)-> {
					cache.put(quote.getSymbol(), quote);
				});
				log.info("added symbols to cache:"+syms);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private Observable<List<String>> readRemoteSymbols(List<List<String>> subLists) {
		return Observable.create(sub-> {
			try {
				subLists.forEach(s1->sub.onNext(s1));
			//	if(true) throw new NullPointerException("jjj");
				sub.onCompleted();
				}
		catch (Exception e) {
			log.error("error occred while getting remote symbols",e);
				sub.onError(e);
		}
		});
	}
	
	public void loadStocks() {
		List<String> symbols = remoteSymbols();
		List<List<String>> subLists = Utils.sublist(symbols, 100);
		Cache cache = cacheManager.getCache("stockCache");
		subLists.stream()
		//.limit(1)
		.forEach(syms-> {
			try {
				List<Quote> quotes =  iExtradingMktData.quotes(syms);
				quotes.forEach((Quote quote)->	cache.put(quote.getSymbol(), quote));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@Cacheable(value = "stockCache",key = "#symbol")
	public Quote getStockBySymbol(String symbol) {
	//this will not execute
		Cache cache = cacheManager.getCache("stockCache");
		return (Quote)cache.get(symbol);
		//return null;
	}
	
	public List<Sector> sectors() throws JsonParseException, JsonMappingException, IOException {
		return iExtradingMktData.sectors();
	}
	
	public Collection<Quote> stocks() {
		Cache cache = cacheManager.getCache("stockCache");
		IMap<String, Quote> mapConfig = (IMap) cache.getNativeCache();
		return mapConfig.values();
	}
	
	private Observable<List<String>> readFromCsv() {
		try {
			log.info("read from csv:");
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("data/symbols.txt").getFile());
			List<String> symbols = Files.readAllLines(file.toPath());
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
