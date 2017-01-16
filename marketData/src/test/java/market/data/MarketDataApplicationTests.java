package market.data;

import market.MarketDataApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MarketDataApplication.class)
public class MarketDataApplicationTests {

	@Test
	public void contextLoads() {
	}

}
