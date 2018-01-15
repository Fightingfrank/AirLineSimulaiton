package japrc2017.util;

/*
 * ExamNumber : Y3851551
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Plane;
import japrc2017.Traffic;



public final class ReplayEvenRecordJsonUtil {
	
	
	private static JSONObject jsonObject;
	
	public static final TickEventReplay getFormatTickEventReplay(JSONObject jsob) {
		jsonObject = jsob;
		
		TickEventReplay tickEventReplay = new TickEventReplay();
		
		tickEventReplay.setAirportList(formatAirportList(jsob.getJSONArray("airportList")));
		tickEventReplay.setAirportCreateList(formatAirportList(jsob.getJSONArray("airportCreateList")));
		tickEventReplay.setTrafficList(formatTraffic(jsob.getJSONArray("trafficList")));
		tickEventReplay.setPlaneList(formatPlaneList(jsob.getJSONArray("planeList")));
		tickEventReplay.setPlaneCreateList(formatPlaneList(jsob.getJSONArray("planeCreateList")));
		tickEventReplay.setPlaneLandList(formatPlaneList(jsob.getJSONArray("planeLandList")));
		tickEventReplay.setMoveMentList(formatMovementList(jsob.getJSONArray("moveMentList")));
		
		return tickEventReplay;
	}
	
	
	public static final List<IAirport> formatAirportList(JSONArray airPortJsonArray){
		
		List<IAirport> airportList = new ArrayList<IAirport> ();
		
		for(int i = 0; i < airPortJsonArray.length() ; i++) {
			JSONObject object = airPortJsonArray.getJSONObject(i);
			String str[] = object.getString("location").split(",");
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			Airport apt = new Airport(object.getString("code"), object.getString("name"), new GridLocation(x,y), object.getDouble("takeOffProbability"));
	
			apt.setPlaneNumber(object.getInt("planeNumber"));
			apt.setStatus(object.getBoolean("status"));
			airportList.add(apt);
		}
		
		return airportList;
	}
	
	public static final List<IPlane> formatPlaneList(JSONArray planeJsonArray){
		
		List<IPlane> planeList = new ArrayList<IPlane> ();
		for( int i = 0 ; i < planeJsonArray.length() ; i++) {
			JSONObject object = planeJsonArray.getJSONObject(i);
			String str[] = object.getString("location").split(",");
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			Plane plane = new Plane(object.getString("callsign"), new GridLocation(x,y), object.getString("originalAirport"), object.getString("destinationAirport"));
			plane.setSpeed(object.getDouble("speed"));
			planeList.add(plane);
		}
		return planeList;
	}
	
	public static final List<Traffic> formatTraffic(JSONArray trafficJsonArray){
		List<Traffic> trafficList = new ArrayList<Traffic> ();
		for( int i = 0 ; i < trafficJsonArray.length() ; i++) {
			JSONObject object = trafficJsonArray.getJSONObject(i);
			String str[] = object.getString("location").split(",");
			int x = Integer.parseInt(str[0]);
			int y = Integer.parseInt(str[1]);
			Traffic traffic = new Traffic(object.getString("callsign"), object.getString("origin"), object.getString("destination"), new GridLocation(x,y));
			trafficList.add(traffic);
		}
		return trafficList;
	}
	
	
	public static final List<Movement> formatMovementList(JSONArray movementJsonArray){
		List<Movement> movementList = new ArrayList<Movement> ();
		for( int i = 0 ; i < movementJsonArray.length() ; i++) {
			JSONObject object = movementJsonArray.getJSONObject(i);
			String str1[] = object.getString("oldLocation").split(",");
			String str2[] = object.getString("newLocation").split(",");
			int x1 = Integer.parseInt(str1[0]);
			int y1 = Integer.parseInt(str1[1]);
			int x2 = Integer.parseInt(str2[0]);
			int y2 = Integer.parseInt(str2[1]);
			Movement movement = new Movement(object.getString("callsign"), new GridLocation(x1,y1), new GridLocation(x2,y2), object.getString("originalAirport"),
					object.getString("destinationAirport"));
			movementList.add(movement);
		}
		return movementList;
	}
}
