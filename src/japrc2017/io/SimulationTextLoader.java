package japrc2017.io;

/*
 * ExamNumber : Y3851551
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.Traffic;
import japrc2017.util.TickEventReplay;

public class SimulationTextLoader implements ISimulationLoader{
	
	private FileInputStream airports = null;
	private FileInputStream traffic = null;
	private BufferedReader airportsReader = null;
	private BufferedReader trafficReader = null;
	private String airportLine, trafficLine;
	
	public SimulationTextLoader (InputStream airports, InputStream traffic) {
		
		this.airports = (FileInputStream) airports;
		this.traffic = (FileInputStream) traffic;
	}
	
	
	public void initializeInputStream() {
			trafficReader = new BufferedReader(new InputStreamReader(traffic));
			airportsReader = new BufferedReader(new InputStreamReader(airports));
	}
	
	@Override
	public boolean hasNextAiport() {
		// TODO Auto-generated method stub
		try {
			if((airportLine = airportsReader.readLine()) != null )
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean hasNextTraffic() {
		// TODO Auto-generated method stub
		try {
			if((trafficLine = trafficReader.readLine()) != null)
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public IAirport nextAirport() {
		// TODO Auto-generated method stub
		String[] str = airportLine.split(",");
		int x = Integer.parseInt(str[2]);
		int y = Integer.parseInt(str[3]);
		double proba = Double.parseDouble(str[4]);
		return new Airport(str[0],str[1],new GridLocation(x,y), proba);
	}

	@Override
	public Traffic nextTraffic() {
		// TODO Auto-generated method stub
		String[] str = trafficLine.split(",");
		int x = Integer.parseInt(str[3]);
		int y = Integer.parseInt(str[4]);
		return new Traffic(str[0],str[1],str[2], new GridLocation(x,y));
	}
	
	public void closeAll() throws IOException {
		if(this.airportsReader!=null) {
			this.airportsReader.close();
		}
		if(this.trafficReader!=null) {
			this.trafficReader.close();
		}
		if(this.traffic!=null) {
			this.traffic.close();
		}
		if(this.airports!=null) {
			this.airports.close();
		}
	}
}
