package market.data.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.csvreader.CsvReader;

public class YahooDownloader  {

	private String doCall(String uri) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);

 
		try {
			HttpResponse response1 = httpClient.execute(httpGet);
 
			 System.out.println(response1.getStatusLine());
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

	private String buildURI(String product) {
		StringBuilder uri = new StringBuilder();
		uri.append("http://finance.yahoo.com/d/quotes.csv?s=GOOGL,YHOO,MS,FB&f=abonskjld2");
		
 
		return uri.toString();
	}
	
	private void extractVals(String responseBody) throws IOException {
		CsvReader csvReader = new CsvReader(new StringReader(responseBody));

		while (csvReader.readRecord()) {
			
			String dateStr = csvReader.get(0);
			String openStr = csvReader.get(1);
			String highStr = csvReader.get(2);
			String lowStr = csvReader.get(3);
			String closeStr = csvReader.get(4);
			String volumeStr = csvReader.get(5);
			String adjStr = csvReader.get(6) + "|"+ csvReader.get(7)+ "|"+ csvReader.get(8);
 
			System.out.println(dateStr + " |"+ openStr + " |"+highStr + " | "+ lowStr + " |"+closeStr + "| "+volumeStr + "|"+adjStr);
			

		}
	}
	
	public static void main(String[] args) throws Exception, IOException {
		YahooDownloader downloader = new YahooDownloader();
		downloader.doYahooDownload();
	}
	private void doYahooDownload() throws Exception, IOException {
		
 
		// creating the request URI
		String uri = buildURI("GOOG");
 
		System.out.println("calling :" + uri);
 
		// doing the call
		String responseBody = doCall(uri);
		extractVals(responseBody);
		// System.out.println(responseBody);
	}
}
