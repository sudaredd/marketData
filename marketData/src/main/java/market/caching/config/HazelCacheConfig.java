package market.caching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

//@Profile({"hazelcast-cache","default"})
@Configuration
public class HazelCacheConfig {

	@Bean
	public Config hazelCastConfig() {
		Config config = new Config();
		config.setInstanceName("hazelcast-cache");

		MapConfig allUsersCache = new MapConfig("alluserscache");
		allUsersCache.setTimeToLiveSeconds(2000);
		allUsersCache.setEvictionConfig(new EvictionConfig()
				.setEvictionPolicy(EvictionPolicy.LFU)
				.setMaxSizePolicy(MaxSizePolicy.PER_NODE)
				.setSize(5000));
		config.addMapConfig(allUsersCache);

		MapConfig usercache = new MapConfig("usercache");
		usercache.setTimeToLiveSeconds(2000);
		usercache.setEvictionConfig(new EvictionConfig()
				.setEvictionPolicy(EvictionPolicy.LFU)
				.setMaxSizePolicy(MaxSizePolicy.PER_NODE)
				.setSize(5000));
		config.addMapConfig(usercache);

		MapConfig stockCache = new MapConfig("stockCache");
		stockCache.setTimeToLiveSeconds(2000);
		stockCache.setEvictionConfig(new EvictionConfig()
				.setEvictionPolicy(EvictionPolicy.LFU)
				.setMaxSizePolicy(MaxSizePolicy.PER_NODE)
				.setSize(5000));
		config.addMapConfig(stockCache);

		return config;
	}
}
