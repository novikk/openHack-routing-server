package openhack.rest;
import java.util.Set;

import com.graphhopper.*;
import com.graphhopper.routing.util.*;
import com.graphhopper.util.*;
import com.graphhopper.util.shapes.GHPoint3D;

class SRWeighting implements Weighting 
{
    private final FlagEncoder encoder;
    private final double maxSpeed;
    private Set<SRPollutionData> pts;

    public SRWeighting( FlagEncoder encoder, Set<SRPollutionData> pts)
    {
        this.encoder = encoder;
        this.maxSpeed = encoder.getMaxSpeed();
        this.pts = pts;
    }

    @Override
    public double getMinWeight( double distance )
    {
        return distance / maxSpeed;
    }

    @Override
    public double calcWeight( EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId )
    {
    	double speed = reverse ? encoder.getReverseSpeed(edgeState.getFlags()) : encoder.getSpeed(edgeState.getFlags());
        if (speed == 0)
            return Double.POSITIVE_INFINITY;
    	
    	PointList pl = edgeState.fetchWayGeometry(3);
    	for (SRPollutionData d : this.pts) {
			//System.out.println(d.getLatitude() + " " + d.getLongitude());

    		for (GHPoint3D p : pl) {
    			// (x - center_x)^2 + (y - center_y)^2 < radius^2
    			double xc = p.getLat() - d.getLatitude();
    			double yc = p.getLon() - d.getLongitude();
    			if (xc * xc + yc * yc < 0.002 * 0.002) {
    				return edgeState.getDistance() * d.getValue() / speed;
    			}
    		}
    	}

        
        return edgeState.getDistance() / speed;
    }

    @Override
    public String toString()
    {
        return "fastest";
    }
    
    @Override
    public boolean matches(String weightingAsStr, FlagEncoder encoder) {
        return getName().equals(weightingAsStr) && encoder == this.encoder;
    }
    @Override
    public FlagEncoder getFlagEncoder() {
        return this.encoder;
    }

    @Override
    public String getName() {
        return "fastest";
    }
    
}