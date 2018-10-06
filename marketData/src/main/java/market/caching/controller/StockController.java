package market.caching.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import market.caching.repository.StockDataService;
import market.data.model.Quote;
import market.data.model.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

	@Autowired
    private StockDataService stockDataService;
	
	    @RequestMapping(path = "/stocks/", method = RequestMethod.GET)
	    public Quote findByFirstNameLastName(String symbol ) {
	        return stockDataService.getStockBySymbol(symbol);
	    }
	    
	    @RequestMapping(path="stocks/all", method = RequestMethod.GET)
	    public Collection<Quote> stocks() {
	    	return stockDataService.stocks();
	    }
	    
	    @RequestMapping(path="/stocks/prices", method = RequestMethod.GET)
	    public Collection<Quote> stocksWithPriceFilter(double price) {
	    	return stocks().stream().filter(s->Double.parseDouble(s.getLow()) > price).collect(Collectors.toList());
	    }

}
