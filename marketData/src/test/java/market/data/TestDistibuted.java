package market.data;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class TestDistibuted {

	public static void main(String[] args) {

		  HazelcastInstance h1 = Hazelcast.newHazelcastInstance();

		// get the map and put 1000 entries
		  Map map1 = h1.getMap( "testmap" );
		  for ( int i = 0; i < 1000; i++ ) {
		    map1.put( i, "value" + i );
		  }
		  // check the map size
		  System.out.println(1000 +":"+ map1.size() );
	}

}
