package openhack.rest;

import java.util.HashSet;
import java.util.Set;

import com.graphhopper.routing.util.EncodingManager;

class GHSingleton {
	public static SRGraphHopper singleton = null;
	
	private GHSingleton() {
		
	}
	
	public static SRGraphHopper getInstance() {
		 if(singleton == null) {
			 Set<SRPollutionData> d = new HashSet<SRPollutionData>();
			 singleton = new SRGraphHopper(d);
			 singleton.setOSMFile("./santander_spain.osm.pbf");
			 singleton.setGraphHopperLocation("./");
			 singleton.setEncodingManager(new EncodingManager("foot"));
			 singleton.importOrLoad();
			 //singleton.load("");
	      }
	      return singleton;
	}
	
	
}