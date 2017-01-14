package market.data.download;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import market.data.model.Symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class StockDownLoad {

    private static final Logger logger = LoggerFactory.getLogger(StockDownLoad.class);

	public static void main(String[] args) {

		new StockDownLoad().remoteSymbols();
	}

	public List<String> remoteSymbols() {
		RestTemplate template = new RestTemplate();
		String url = "https://raw.githubusercontent.com/datasets/s-and-p-500-companies/master/data/constituents.csv";
		String symbols = template.getForObject(url, String.class);
		return parse(symbols);
	}

	private List<String> parse(String symbols) {
		String lines[] = symbols.split("\\r?\\n");
		return Arrays.stream(lines).
		map(s->s.split(",")).
		map((String []s) -> s[0]).		
		collect(Collectors.toList());
	}
	
	/*private List<Symbol> parse(String symbols) {
		String lines[] = symbols.split("\\r?\\n");
		return Arrays.stream(lines).
		map(s->s.split(",")).
		map((String []s) -> new Symbol(s[0], s[1], s[2])).		
		collect(Collectors.toList());
	}*/

}
