package market.caching.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import market.caching.model.UserPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SImpleDataConfig {

	 @Autowired
	    private ObjectMapper objectMapper;

	    @Value("classpath:/users.json")
	    private Resource usersJsonResource;
	    
	    @Bean
	    public List<UserPayload> payloadUsers() throws IOException {

	        try(InputStream inputStream = usersJsonResource.getInputStream()) {
	            UserPayload[] payloadUsers = objectMapper.readValue(inputStream,UserPayload[].class);
	            return Arrays.asList(payloadUsers);
	        }
	    }
}
