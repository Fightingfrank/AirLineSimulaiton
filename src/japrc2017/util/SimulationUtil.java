package japrc2017.util;


/*
 * ExamNumber : Y3851551
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Plane;
import japrc2017.Traffic;

public final class SimulationUtil {
	public static final String createDestinationAirportRandomly(Airport originalAirport, List<IAirport> airportList) {
		Airport destinationAirport;
		while(true) {
			int i = (int)(Math.random()*airportList.size());
			destinationAirport = (Airport)airportList.get(i);
			if(!destinationAirport.getCode().equals(originalAirport.getCode())) {
				break;
			}
		}
		
		return destinationAirport.getCode();
	}
	
	public static final String callsignCreate(String code, int planeNumber) {
		if(planeNumber < 10) {
			return code+"_00"+planeNumber;
		}else if(planeNumber >= 10 && planeNumber <=99) {
			return code+"_0"+planeNumber;
		}else return code+"_"+planeNumber;
	}
	
	public static final int calculateFlyTicksCost(Plane plane) {
		
		return (int)Math.ceil(calculateFlyDistance(plane)/plane.getSpeed());
	}
	
	public static final double calculateFlyDistance(Plane plane) {
		double dx = ((Airport)plane.getDestination()).getLocation().getX();
		double dy = ((Airport)plane.getDestination()).getLocation().getY();
		double ox = ((Airport)plane.getOrigin()).getLocation().getX();
		double oy = ((Airport)plane.getOrigin()).getLocation().getY();
		double x = Math.pow(Math.abs(dx-ox), 2);
		double y = Math.pow(Math.abs(dy-oy), 2);
		double distance = Math.sqrt(x+y);
		
		DecimalFormat df = new DecimalFormat("#0.00");
	
		return Double.parseDouble(df.format(distance));
	}
	
	public static final GridLocation calculateNextLocation(Plane plane) {
		
		//because the real location
		double dx = ((Airport)plane.getDestination()).getLocation().getX();
		double dy = ((Airport)plane.getDestination()).getLocation().getY();
		double ox = plane.getRealX();
		double oy = plane.getRealY();
		double distance = SimulationUtil.calculateFlyDistance(plane);
		
		double perTickMoveDistance = plane.getPathLength();
		double x=0,y=0;
		if(dx == ox) {
			x = ox;
			if(dy > oy) {
				y = oy + plane.getSpeed();
			}else if(dy < oy) {
				y = oy - plane.getSpeed();
			}
		}else if(dy == oy) {
			y = oy;
			if(dx > ox) {
				x = ox + plane.getSpeed();
			}else if( dx < ox) {
				x = ox - plane.getSpeed();
			}
		}else if(dx < ox) {
			if(dy < oy) {
				x = ox - (perTickMoveDistance*(ox - dx))/distance;
				y = oy - (perTickMoveDistance*(oy - dy))/distance;
			}else if(dy > oy) {
				x = ox - (perTickMoveDistance*(ox - dx))/distance;
				y = oy + (perTickMoveDistance*(dy - oy))/distance;
			}
		}else if(dx > ox) {
			if(dy <= oy) {
				x = ox + (perTickMoveDistance*(dx - ox))/distance;
				y = oy - (perTickMoveDistance*(oy - dy))/distance;
			}else if(dy > oy) {
				x = ox + (perTickMoveDistance*(dx - ox))/distance;
				y = oy + (perTickMoveDistance*(dy - oy))/distance;
			}
		}
		
		plane.setRealX(x);
		plane.setRealY(y);
		return new GridLocation((int)x,(int)y);
		
	}
	
	public static final String getFilePath(String filename) {
		return filename;
	}
	
	public static final InputStream generalteInputStream(String filename) throws FileNotFoundException {
		return new FileInputStream(SimulationUtil.getFilePath(filename));
	}
	
	public static final OutputStream generalteOutputStream(String filename) throws IOException {
		File file = new File(SimulationUtil.getFilePath(filename));
		if(file.exists() == false) {
			file.createNewFile();
		}
		return new FileOutputStream(file);
	}
	
	
	public static Vector<String> generateArrivals(List<Traffic> traffic, String airport){
		Vector<String> str = new Vector<String> ();
		
		for (Traffic  t: traffic) {
			if(airport.equals(t.getDestination())) {
				String tmp = t.getCallsign() + " " + t.getLocation().toString() + " --> "  +airport;  
				str.add(tmp);
			}
		}
		return str;
	}
	
	public static Vector<String> generateArrivalsReplayMode(List<IPlane> plane, String airport ){
		Vector<String> str = new Vector<String> ();
		
		for(IPlane t: plane) {
			if(airport.equals(t.getDestination().getCode())) {
				String tmp = t.getName() + " " + t.getLocation().toString() + " --> " + airport;
				str.add(tmp);
			}
		}
		return str;
	}
	
	public static Vector<String> generateDepartures(List<Traffic> traffic, String airport){
		Vector<String> str = new Vector<String> ();
		
		for(Traffic t: traffic) {
			if(airport.equals(t.getOrigin())) {
				String tmp = t.getCallsign() + " " + t.getLocation().toString() + " --> " + t.getDestination();
				str.add(tmp);
			}
		}
		return str;
	}
	
	public static Vector<String> generateDeparturesReplayMode(List<IPlane> plane, String airport){
		Vector<String> str = new Vector<String> ();
		for(IPlane t: plane) {
			if(airport.equals(t.getOrigin().getCode())) {
				String tmp = t.getName() + " " + t.getLocation().toString() + " --> " + airport;
				str.add(tmp);
			}
		}
		
		return str;
	}
	
	public static final String matchCode(String str) {
		Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(str);
        String matchedStr = null;
        while (matcher.find()) {
            matchedStr = matcher.group();
            break;
        }
        return matchedStr;
	}
	
	public static final List<IAirport> generateAirportJsonFile(JSONArray jsonAirport){
		List<IAirport> airportList = new ArrayList<IAirport>();
		for(int i = 0 ; i < jsonAirport.length() ; i++) {
			JSONObject jsob = jsonAirport.getJSONObject(i);
			String code = jsob.getString("code");
			String name = jsob.getString("name");
			int x = Integer.parseInt(jsob.getString("location").split(",")[0]);
			int y = Integer.parseInt(jsob.getString("location").split(",")[1]);
			double pro = jsob.getDouble("takeOffProbability");
			airportList.add(new Airport(code,name,new GridLocation(x,y), pro));
		}
		return airportList;
	}
	
	public static final List<Traffic> generateTrafficJsonFile(JSONArray jsonTraffic){
		List<Traffic> trafficList = new ArrayList<Traffic>();
		for(int i = 0 ; i < jsonTraffic.length() ; i++) {
			JSONObject jsob = jsonTraffic.getJSONObject(i);
			String callsign = jsob.getString("callsign");
			String originalAirport = jsob.getString("originalAirport");
			String destinationAirport = jsob.getString("destinationAirport");
			int x = Integer.parseInt(jsob.getString("location").split(",")[0]);
			int y = Integer.parseInt(jsob.getString("location").split(",")[1]);
			trafficList.add(new Traffic(callsign,originalAirport,destinationAirport,new GridLocation(x, y)));
		}
		return trafficList;
	}
	
	
}
