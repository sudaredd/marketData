package market;

import market.caching.config.HazelCacheConfig;
import market.caching.config.SImpleDataConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration
@Import({HazelCacheConfig.class,SImpleDataConfig.class})
//@EnableScheduling
@EnableCaching
@SpringBootApplication
public class MarketDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketDataApplication.class, args);
	}
}
