package japrc2017.util;

/*
 * ExamNumber : Y3851551
 */

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import japrc2017.Airport;
import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Plane;
import japrc2017.Traffic;

public class GenerateJSONFile {
	
	public static final JSONArray generateAirportJSONArray(List<IAirport> airportList) {
		JSONArray jsonarry = new JSONArray();
	
		// "code": "LHR",
        //"name": "Manchester",
        //"location": "20,30",
        //"status": false,
        //"planeNumber": 1,
        //"takeOffProbability": 0
		for(int i = 0 ; i < airportList.size() ; i++) {
			JSONObject object = new JSONObject();
			Airport airport = (Airport)airportList.get(i);
			object.put("code", airport.getCode());
			object.put("name",airport.getName());
			object.put("location", ""+airport.getLocation().getX() + "," + airport.getLocation().getY());
			object.put("status", airport.getOpen());
			object.put("planeNumber", airport.getPlaneNumber());
			object.put("takeOffProbability", airport.getTakeOffProbability());
			jsonarry.put(object);
		}
		return jsonarry;
	}
	
	// "callsign": "LHR_001",
    //"location": "10,10",
//    "originalAirport": "LHR",
//    "destinationAirport": "YK",
//    "speed" : 1.0
	public static final JSONArray generatePlaneJSONArray(List<IPlane> planeList) {
		JSONArray jsonarray = new JSONArray();
		
		for(int i= 0 ; i < planeList.size(); i++) {
			JSONObject object = new JSONObject();
			Plane plane = (Plane)planeList.get(i);
			object.put("callsign", plane.getName());
			object.put("location", plane.getLocation().getX() + "," + plane.getLocation().getY());
			object.put("originalAirport", plane.getOrigin().getCode());
			object.put("destinationAirport", plane.getDestination().getCode());
			object.put("speed", plane.getSpeed());
			jsonarray.put(object);
		}
		return jsonarray;
	}
	
//	"callsign": "XR_001",
//    "destination": "LOND",
//    "origin": "XRODN",
//    "location": "15,15"
	public static final JSONArray generateTrafficJSONArray(List<Traffic> trafficList) {
		JSONArray jsonarray = new JSONArray();
		
		for(int i = 0 ; i< trafficList.size(); i++) {
			JSONObject object = new JSONObject();
			Traffic traffic = trafficList.get(i);
			object.put("callsign", traffic.getCallsign());
			object.put("destination", traffic.getDestination());
			object.put("origin", traffic.getOrigin());
			object.put("location", traffic.getLocation().getX() + ","+ traffic.getLocation().getY());
			jsonarray.put(object);
		}
		return jsonarray;
	}
	
//    "callsign": "LHR_003",
//    "oldLocation": "20,20",
//    "newLocation": "30,30",
//    "originalAirport": "LHR",
//    "destinationAirport": "XD"
	public static final JSONArray generateMovementJSONArray(List<Movement> movementList) {
		JSONArray jsonarray = new JSONArray();
		
		for(int i = 0; i < movementList.size() ; i ++) {
			JSONObject object = new JSONObject();
			Movement movement = movementList.get(i);
			object.put("callsign", movement.getCallsign());
			object.put("oldLocation", movement.getOldLocation().getX() + "," + movement.getOldLocation().getY());
			object.put("newLocation", movement.getNewLocation().getX() + "," + movement.getNewLocation().getY());
			object.put("originalAirport", movement.getOriginalAirport());
			object.put("destinationAirport", movement.getDestinationAirport());
			jsonarray.put(object);
		}
		return jsonarray;
	}
	
	
}
