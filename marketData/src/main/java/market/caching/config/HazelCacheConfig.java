package market.caching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;

//@Profile({"hazelcast-cache","default"})
@Configuration
public class HazelCacheConfig {

	@Bean
	public Config hazelCastConfig() {
		Config config = new Config();
		config.setInstanceName("hazelcast-cache");

		MapConfig allUsersCache = new MapConfig();
		allUsersCache.setTimeToLiveSeconds(2000);
		allUsersCache.setEvictionPolicy(EvictionPolicy.LFU);
		config.getMapConfigs().put("alluserscache", allUsersCache);

		MapConfig usercache = new MapConfig();
		usercache.setTimeToLiveSeconds(2000);
		usercache.setEvictionPolicy(EvictionPolicy.LFU);
		config.getMapConfigs().put("usercache", usercache);

		MapConfig stockCache = new MapConfig();
		usercache.setTimeToLiveSeconds(2000);
		usercache.setEvictionPolicy(EvictionPolicy.LFU);
		config.getMapConfigs().put("stockCache", stockCache);

		return config;
	}
}
