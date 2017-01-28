package market.data;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class TestDistibutedClient {

	public static void main(String[] args) {

		 HazelcastInstance h1 = Hazelcast.newHazelcastInstance();

			// get the map and put 1000 entries
			  Map map1 = h1.getMap( "testmap" );
			  System.out.println(1000 +" in escond map:"+ map1.size() );
			  map1.forEach((k,v)-> System.out.println("key:"+k+" ,vlue:"+v));

	}

}
