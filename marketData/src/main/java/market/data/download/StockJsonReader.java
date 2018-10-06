package market.data.download;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import market.data.model.Quote;

public class StockJsonReader {
	ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {


		byte[] quotes = Files.readAllBytes(Paths.get("C:\\Users\\Sudarsana\\Desktop\\txt","quote.json"));
		Map<?, Map<String, Quote>> map = new StockJsonReader().quotes(quotes);

		map.entrySet().forEach(v->System.out.println("key:"+v.getKey() + " ,value:"+v.getValue().get("quote")));
	}

	public Map<?, Map<String, Quote>> quotes( byte[] quotes)
			throws IOException, JsonParseException, JsonMappingException {
	//	Map<?,?> map = mapper.readValue(quotes , Map.class);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		Map<?, Map<String, Quote>> map = mapper.readValue(quotes , new TypeReference<Map<?, Map<String, Quote>>>(){});
		return map;
	}

}
