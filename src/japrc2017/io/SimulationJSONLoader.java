package japrc2017.io;

/*
 * ExamNumber : Y3851551
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Traffic;
import japrc2017.util.ReplayEvenRecordJsonUtil;
import japrc2017.util.SimulationUtil;
import japrc2017.util.TickEventReplay;

public class SimulationJSONLoader implements ISimulationLoader{
	
	private List<TickEventReplay> replayEventList;
	private List<IAirport> airportList;
	private List<Traffic> trafficList;
	int i;
	int j;
	public List<TickEventReplay> getReplayEventList() {
		return replayEventList;
	}
	private BufferedReader reader;
	
	public SimulationJSONLoader(InputStream data) {
		this.reader = new BufferedReader(new InputStreamReader(data));
		i = 0; j = 0;
	}
	
	public void initializeJsonData() {
		String line = "",result="";
		try {
			while((line=reader.readLine())!=null){
				result += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject object = new JSONObject(result);
		
		airportList = SimulationUtil.generateAirportJsonFile(object.getJSONArray("airports"));
		trafficList = SimulationUtil.generateTrafficJsonFile(object.getJSONArray("traffic"));
	}
	
	@Override
	public boolean hasNextAiport() {
		if(i<airportList.size() && airportList.get(i)!= null  )
			return true;
		else return false;
	}

	@Override
	public boolean hasNextTraffic() {
		if(j<trafficList.size() && trafficList.get(j)!=null) 
			return true;
		else return false;
	}

	@Override
	public IAirport nextAirport() {
		return airportList.get(i++);
	}

	@Override
	public Traffic nextTraffic() {
		return trafficList.get(j++);
	}
	
	public void closeAll() {
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
