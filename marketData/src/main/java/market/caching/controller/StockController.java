package market.caching.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import market.caching.repository.StockDataService;
import market.data.model.Quote;
import market.data.model.Sector;

@RestController
public class StockController {

	@Autowired
	private StockDataService stockDataService;

	@RequestMapping(path = "/stocks/", method = RequestMethod.GET)
	public Quote stockBySymbol(String symbol) {
		return stockDataService.getStockBySymbol(symbol);
	}

	@RequestMapping(path = "stocks/all", method = RequestMethod.GET)
	public Collection<Quote> stocks() {
		return stockDataService.stocks();
	}

	@RequestMapping(path = "/sectors/all", method = RequestMethod.GET)
	public Collection<Sector> sectors() throws JsonParseException, JsonMappingException, IOException {
		return stockDataService.sectors();
	}

	@RequestMapping(path = "/stocks/prices", method = RequestMethod.GET)
	public Collection<Quote> stocksWithPriceFilter(double price) {
		return stocks().stream().filter(s -> Double.parseDouble(s.getLow()) > price).collect(Collectors.toList());
	}

}
