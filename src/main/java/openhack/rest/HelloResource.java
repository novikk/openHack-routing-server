package openhack.rest;

import openhack.domain.Message;
import openhack.Roles;
import org.joda.time.DateTime;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.AlgorithmOptions;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint3D;

import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;
import restx.security.RestxSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Component @RestxResource
public class HelloResource {
	public HelloResource() {
		System.out.println("test");			
	}

    /**
     * Say hello to anybody.
     *
     * Does not require authentication.
     *
     * @return a Message to say hello
     */
    @GET("/newRoute")
    @PermitAll
    public Message helloPublic(String tp, int km, double fromLat, double fromLon) {
    	SRGraphHopper gh = GHSingleton.getInstance();
    	
    	double newLon = fromLon + (((double)km/2)/6378)*(180/Math.PI) / Math.cos(fromLat*Math.PI/180);
    	GHRequest req = new GHRequest(fromLat, fromLon, fromLat, newLon)
        .setAlgorithm(AlgorithmOptions.DIJKSTRA_BI)
        .setVehicle("foot")
        .setWeighting("fastest");

        req.getHints().put("instructions", "true");
        GHResponse resp = gh.route(req);
        PathWrapper pathWrapper = resp.getBest();
        
        String res = "";
        PointList pl = pathWrapper.getPoints();
        for (GHPoint3D p : pl) {
        	res = res + "[" + p.getLon() + "," + p.getLat() +"],";
        }
        
        return new Message().setMessage("[" + res + "]");
    }
}
