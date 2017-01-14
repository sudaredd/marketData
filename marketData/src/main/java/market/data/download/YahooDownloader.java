package market.data.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import market.data.model.Stock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.csvreader.CsvReader;

public class YahooDownloader  {

	private static Logger log = Logger.getLogger(YahooDownloader.class);

	private String doCall(String uri) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response1 = httpClient.execute(httpGet);
			 log.info(response1.getStatusLine());
	         HttpEntity entity1 = response1.getEntity();
			if (response1.getStatusLine().getStatusCode() != 200) {
				throw new Exception("HTTP problem, httpcode: "+ response1);
			}
 
			InputStream stream =entity1.getContent();
			String responseText = responseToString(stream);
			return responseText;
 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return null;
	}
	
	private String responseToString(InputStream stream) throws IOException {
		BufferedInputStream bi = new BufferedInputStream(stream);
		 
		StringBuilder sb = new StringBuilder();
 
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = bi.read(buffer)) != -1) {
			sb.append(new String(buffer, 0, bytesRead));
		}
		return sb.toString();
	}

	/**
	 * http://wern-ancheta.com/blog/2015/04/05/getting-started-with-the-yahoo-finance-api/
	 * @param product
	 * @return
	 */
	private String buildURI(List<String> symbols) {
		String syms = String.join(",", symbols );
		StringBuilder uri = new StringBuilder();
		uri.append("http://finance.yahoo.com/d/quotes.csv?s="+syms+"&f=abopnskjld1");
		return uri.toString();
	}
	
	private List<Stock> extractVals(String responseBody) throws IOException {
		CsvReader csvReader = new CsvReader(new StringReader(responseBody));
		List<Stock> stocks = new ArrayList<Stock>();
		while (csvReader.readRecord()) {
			 double ask = parseDouble(csvReader.get(0));
			 double bid  = parseDouble(csvReader.get(1));
			 double open = parseDouble(csvReader.get(2));
			 double previousClose= parseDouble(csvReader.get(3));
			 String name = csvReader.get(4);
			 String symbol = csvReader.get(5);
			 double _52_week_high= parseDouble(csvReader.get(6));
			 double _52_week_low= parseDouble(csvReader.get(7));
			 String lastTradeWithTime = csvReader.get(8);
			 String lastTradeDate = csvReader.get(9);

			 stocks.add(new Stock(ask, bid, open, previousClose, name, symbol, _52_week_high, _52_week_low, lastTradeWithTime, lastTradeDate));
		}
		
		return stocks;
	}
	
	private double parseDouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch(NumberFormatException e) {
			return 0;
		}
	}

	public static void main(String[] args) throws Exception, IOException {
		YahooDownloader downloader = new YahooDownloader();
		List<Stock> stocks = downloader.doYahooDownload(Arrays.asList("GOOGL","YHOO","FB","MS"));
		stocks.forEach(s->System.out.println(s));

	}
	public List<Stock> doYahooDownload(List<String> symbols) throws Exception, IOException {
		
		// creating the request URI
		String uri = buildURI(symbols);
 
		log.info("calling :" + uri);
		// doing the call
		String responseBody = doCall(uri);
		return extractVals(responseBody);
		
	}
}
