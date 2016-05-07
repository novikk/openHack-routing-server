package openhack.rest;
import java.util.Set;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.util.Weighting;
import com.graphhopper.routing.util.WeightingMap;

class SRGraphHopper extends GraphHopper {
	Set<SRPollutionData> pts;
	
    public SRGraphHopper(Set<SRPollutionData> pts) {
    	System.out.println("Creado GH");
    	this.pts = pts;
    }
    
    @Override
    public Weighting createWeighting( WeightingMap wMap, FlagEncoder encoder )
    {
    	//System.out.println(wMap.getWeighting());
    	return new SRWeighting(encoder, pts);
    }
}