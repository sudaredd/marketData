package market.data.download;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import market.data.model.Quote;
import market.data.model.Sector;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StockJsonReader {
	ObjectMapper mapper = new ObjectMapper();

	public StockJsonReader() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	public Map<?, Map<String, Quote>> quotes( byte[] quotes)
			throws IOException, JsonParseException, JsonMappingException {
		Map<?, Map<String, Quote>> map = mapper.readValue(quotes , new TypeReference<Map<?, Map<String, Quote>>>(){});
		return map;
	}
	
	public List<Sector> sectors(byte[] data) throws JsonParseException, JsonMappingException, IOException {
		List<Sector> sectors = mapper.readValue(data , new TypeReference<List<Sector>>() {});
		return sectors;
	}

}
