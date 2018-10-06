package market.data.download;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import market.data.model.Quote;

@Service
public class IExtradingMktData {
	private static final Logger log = LoggerFactory.getLogger(IExtradingMktData.class);
	
	@Value("${quote.url}")
	private String quoteUrl;
	private String concat = "&types=quote&range=1m&last=5";
	
	public List<Quote> quotes(List<String> symbols) throws JsonParseException, JsonMappingException, IOException {
		RestTemplate template = new RestTemplate();
		String endUrl = quoteUrl+String.join(",", symbols)+concat;
		log.info("sending url:_"+endUrl);
		String data = template.getForObject(endUrl, String.class);
		StockJsonReader jsonReader = new StockJsonReader();
		Map<?, Map<String, Quote>> map = jsonReader.quotes(data.getBytes());
		return map.values().stream().flatMap(e->e.values().stream()).collect(Collectors.toList());
	}
}
