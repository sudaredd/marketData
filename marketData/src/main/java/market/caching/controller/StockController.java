package market.caching.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import market.caching.model.UserPayload;
import market.caching.repository.StockDataService;
import market.caching.repository.UserRepository;
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
	    public Stock findByFirstNameLastName(String symbol ) {

	        return stockDataService.getStockBySymbol(symbol);
	    }

}
