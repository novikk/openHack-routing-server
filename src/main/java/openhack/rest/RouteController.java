package openhack.rest;

import openhack.domain.Message;
import openhack.Roles;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.io.Files;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.routing.AlgorithmOptions;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint3D;

import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;
import restx.security.RestxSession;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Component @RestxResource
public class RouteController {
	public RouteController() {
		System.out.println("test");			
	}

    @GET("/newRoute")
    @PermitAll
    public Message newRoute(String tp, int km, double fromLat, double fromLon) {
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
    
    
    @GET("/newPollution")
    @PermitAll
    public Message newPollution(String pol) {
    	
    	JSONObject obj = new JSONObject(pol);
    	JSONArray arr = obj.getJSONArray("points");
    	Set<SRPollutionData> pts = new HashSet<SRPollutionData>();

    	for (int i = 0; i < arr.length(); i++)
    	{
    		SRPollutionData srpd = new SRPollutionData( arr.getJSONObject(i).getDouble("lat"),  arr.getJSONObject(i).getDouble("lon"),  arr.getJSONObject(i).getDouble("val"));
    		pts.add(srpd);
    		System.out.println(srpd.getLatitude() + " " + srpd.getLongitude() + " " + srpd.getValue());
    	}
    	
    	
    	new File("./edges").delete();
    	new File("./geometry").delete();
    	new File("./location_index").delete();
    	new File("./names").delete();
    	new File("./nodes").delete();
    	new File("./nodes_ch_fastest").delete();
    	new File("./properties").delete();
    	new File("./shortcuts_fastest").delete();
    	
    	SRGraphHopper gh = new SRGraphHopper(pts);
    	gh.setOSMFile("./santander_spain.osm.pbf");
    	gh.setGraphHopperLocation("./");
    	gh.setEncodingManager(new EncodingManager("foot"));
    	gh.importOrLoad();
    	GHSingleton.singleton = gh;
        return new Message().setMessage("ok");
    }
}
