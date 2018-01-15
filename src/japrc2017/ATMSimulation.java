package japrc2017;


/*
 * ExamNumber : Y3851551
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import japrc2017.event.ISimulationPublisher;
import japrc2017.event.SimulationEvent;
import japrc2017.event.SimulationListener;
import japrc2017.io.ISimulationIO;
import japrc2017.io.ISimulationLoader;
import japrc2017.io.ISimulationLogger;
import japrc2017.io.ReplayJsonLoader;
import japrc2017.io.SimulationJSONLoader;
import japrc2017.io.TextLogger;
import japrc2017.util.AirproxIncidentUtil;
import japrc2017.util.GenerateJSONFile;
import japrc2017.util.Movement;
import japrc2017.util.SimulationUtil;
import japrc2017.util.TickEventReplay;

public class ATMSimulation extends Thread implements ISimulation, ISimulationIO, IReplay, ISimulationPublisher , Runnable{
	private List<IPlane> planeList;
	private List<IAirport> airportList;
	private List<Traffic> trafficList;
	private List<AirproxIncidentUtil> airProxIncidentList; //record Incident data
	private List<TickEventReplay> tickEventReplay;  //record all events during replay record period
	private GridLocation mapDimension;
	private int tickDelays;
	private int simtimeTicks;
	private boolean threadStatus = false; //true, thread run, false, thread pause
	private int mode; //0 - normal mode 1 - replay mode
	private int record; //1 - need to record event; 0 - record off 
	private int startRecordTime,stopRecordTime;  //startRecordTime is used to calculate record size
	private int startFlag = 0;
	private String control = "";   //just use this class to synchronized thread
	private SimulationListener simulationListener;
	private SimulationEvent event;
	private SimulationStartThread startThread;
	private List<IPlane> planeCreateList;
	private int writeReplayFileSignal;
	private int replayPauseFlag = 0;
	public ATMSimulation() {
		this.planeList = new ArrayList<IPlane> ();
		this.airportList = new ArrayList<IAirport> ();
		this.trafficList = new ArrayList<Traffic> ();
		this.planeCreateList = new ArrayList<IPlane>();
		this.airProxIncidentList = new ArrayList<AirproxIncidentUtil> ();
		this.mode = 0;
		this.tickEventReplay = new ArrayList<TickEventReplay>();
		this.record = 0;
		this.startRecordTime = -1;
		this.stopRecordTime = -1;
		this.writeReplayFileSignal = 0;
		//create a event, which used to notify GUI class when something has change.
		event = new SimulationEvent(this);
		
	}
	public ATMSimulation(GridLocation mapDimension) {
		this.mapDimension = mapDimension;
		this.planeList = new ArrayList<IPlane> ();
		this.airportList = new ArrayList<IAirport> ();
		this.trafficList = new ArrayList<Traffic> ();
		this.planeCreateList = new ArrayList<IPlane>();
		this.airProxIncidentList = new ArrayList<AirproxIncidentUtil> ();
		this.mode = 0;
		this.tickEventReplay = new ArrayList<TickEventReplay>();
		this.record = 0;
		this.startRecordTime = -1;
		this.stopRecordTime = -1;
		this.writeReplayFileSignal = 0;
		this.record = 0;
		//create a event, which used to notify GUI class when something has change.
		event = new SimulationEvent(this);
	}
	
	private class SimulationStartThread implements Runnable{
		@Override
		public void run() {
			while(true) {
				synchronized(control) {
					if(!threadStatus) {
						try {
							control.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						try {
							if(mode == 1 && simtimeTicks == tickEventReplay.size())  {//replay mode need to control tick in the range of record period
								threadStatus = false ;
								break;   //end while loop, then end Simulation thread
							}
							tick();
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println("Simulation end!  Bye");
			threadStatus = false;
			startFlag = 0;
		}
	}
	
	@Override
	public void tick() {
		//if system at record mode, need to create TickEventReplay entity every tick
		if(record == 1 && mode == 0) {  //record & replay mode can't happen at same time
			if(startRecordTime == -1)
				startRecordTime = simtimeTicks;   
			TickEventReplay replayEvent = new TickEventReplay();
			tickEventReplay.add(replayEvent);
		}		
		planeTakeoff();
		if(mode == 0) {
			for(int i = 0 ; i < planeList.size() ; i++) {
				Plane plane = (Plane)planeList.get(i);
				if(plane.getStatus() == 2){  //already take off plane
					movePlaneTo(plane.getName(), SimulationUtil.calculateNextLocation(plane));
					plane.setPathLength(plane.getPathLength()+plane.getSpeed());
				}
			}	
		}else if(mode == 1) { //follow replayFile movement path
			for(Movement i: tickEventReplay.get(simtimeTicks).getMoveMentList()) {
				System.out.println("移动飞机sim ：" + simtimeTicks);
				this.movePlaneTo(i.getCallsign(), i.getNewLocation());
			}
		}
		checkPlaneLand();
		checkAirproxIncident();
		//notify SimulationListener(GUI) that some data has changed.
		if(simulationListener!=null)
			this.simulationListener.notifySimHasChanged(event);  
		if(record == 1)
			addTickData();
		//add tick num.
		if(writeReplayFileSignal == 1 && record == 1) {
			record = 0;
			try {
				this.setReplayLogFile(SimulationUtil.generalteOutputStream("replayData.json"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		simtimeTicks++;
	}
	
	public void addTickData() {
		tickEventReplay.get(simtimeTicks - startRecordTime).setAirportList(airportList);
		tickEventReplay.get(simtimeTicks - startRecordTime).setPlaneList(planeList);
		tickEventReplay.get(simtimeTicks - startRecordTime).setTrafficList(trafficList);
	}
	
	public void checkAirproxIncident() {
		for(int i = 0; i< planeList.size() ; i++) {
			for(int j = i+1; j < planeList.size() ; j++) {
				Plane plane1 = (Plane)planeList.get(i);
				Plane plane2 = (Plane)planeList.get(j);
				if(plane1.getLocation().equals(plane2.getLocation())) {
					airProxIncidentList.add(new AirproxIncidentUtil(plane1.getName(), plane2.getName(), plane1.getLocation(), this.getSimTime()));
				}
			}
		}
		if(airProxIncidentList.size() > 0) {
			String path = SimulationUtil.getFilePath("airproxIncident.txt");
			File file = new File(path);
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				this.setLogFile(new TextLogger(new FileOutputStream(file,true)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			airProxIncidentList.clear();
		}
	}
	
	public void checkPlaneLand() {
		if(this.mode == 0) { //normal mode
			for(IPlane i: planeList) {
				//if plane's location equals with destinationAirportLocation, then this plane will land
				//record before a plane land operation complete
				Plane plane = (Plane)i;
				if(plane.getLocation().equals(plane.getDestination().getLocation())) {
					planeLand((Plane)i);
					if(record == 1) {
						tickEventReplay.get(simtimeTicks - startRecordTime).getPlaneLandList().add(plane);
					}
				}
			}
		}else if(mode == 1){ //  replay mode
			TickEventReplay replay = tickEventReplay.get(simtimeTicks);
			if(replay.planeLandList.size() !=0) {
				for(IPlane i:replay.planeLandList) {
					planeLand((Plane)i); 
				}
			}
		}
	}
	public void planeLand(Plane plane) {
		if(plane.getStatus() == 3)  {//already landed
			plane.setStatus(4);
		}	
		else if(plane.getStatus() == 2) {//arrive the destination, but need to land at next tick
			plane.setStatus(3); 
		}
	}
	
	public void planeTakeoff() {
		if(this.mode == 0) {  //normal mode
			double tmp = (int)(Math.random()*100);
			if(airportList.size() == 0) {
				return ;
			}
			for(IAirport i: airportList) {
				if(tmp<i.getTakeOffProbability()*100) {
					int number = ((Airport)i).getPlaneNumber();
					String callsign = SimulationUtil.callsignCreate(i.getCode(), number+1);
					String destinationAirport = SimulationUtil.createDestinationAirportRandomly((Airport)i, airportList);
					this.addPlane(callsign, i.getCode(), destinationAirport);  //once add, then take off;
					((Airport)i).setPlaneNumber(number+1);
				}
			}
		}else if(this.mode == 1){ //replay mode
			for(IPlane i: tickEventReplay.get(simtimeTicks).getPlaneCreateList()) {
				((Plane)i).setStatus(2); //plane take off
			}
		}
	}
	
	//the number of ticks since the simulation was started
	@Override
	public int getSimTime() {
		return simtimeTicks;
	}

	@Override
	public GridLocation getMapDimensions() {
		return mapDimension;
	}

	@Override
	public void setMapDimensions(int width, int height) {
		this.mapDimension = new GridLocation(width, height);
	}

	@Override
	public void movePlaneTo(String planeName, GridLocation newLoc) {
		try {
			Plane plane = (Plane)this.getPlane(planeName);
			if(record == 1) {
				Movement movement = new Movement(plane.getName(), plane.getLocation(), newLoc, plane.getOrigin().getCode(), plane.getDestination().getCode());
				if(tickEventReplay == null) {
					System.out.println("tickeventReplay is null");
				}
				tickEventReplay.get(simtimeTicks - startRecordTime).getMoveMentList().add(movement);
			}
			plane.setLocation(newLoc);
		
		}catch(SimulationException c) {
			System.out.println(c.toString());
		}
	}

	@Override
	public void start() {
		
		if(startFlag == 0) {
			startThread = new SimulationStartThread();
			this.threadStatus = true;
			new Thread(startThread).start();;
			startFlag = 1;
		}
		if(!threadStatus) {
			this.threadStatus = true;
			synchronized(control) {
				control.notify();
			}
		}
	}

	@Override
	public void pause() {
		if(threadStatus) {
			this.threadStatus = false;
		}
		
	}
	
	public int getTickDelay() {
		return this.tickDelays;
	}
	@Override
	public void setTickDelay(int millis) {
		this.tickDelays = millis;
	}
	@Override
	public void setReplayLogFile(OutputStream replayStream) {
		JSONArray jsonArray = new JSONArray();
		PrintWriter pw = new PrintWriter((FileOutputStream)replayStream);
		for(int i = 0 ; i < tickEventReplay.size() ; i ++) { //tickeventReplay, JSONArray
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("airportList",GenerateJSONFile.generateAirportJSONArray(tickEventReplay.get(i).getAirportList()));
			jsonObject.put("airportCreateList",GenerateJSONFile.generateAirportJSONArray(tickEventReplay.get(i).getAirportCreateList()));
			jsonObject.put("planeList", GenerateJSONFile.generatePlaneJSONArray(tickEventReplay.get(i).getPlaneList()));
			jsonObject.put("planeCreateList", GenerateJSONFile.generatePlaneJSONArray(tickEventReplay.get(i).getPlaneCreateList()));
			jsonObject.put("planeLandList", GenerateJSONFile.generatePlaneJSONArray(tickEventReplay.get(i).getPlaneLandList()));
			jsonObject.put("trafficList", GenerateJSONFile.generateTrafficJSONArray(tickEventReplay.get(i).getTrafficList()));
			jsonObject.put("moveMentList", GenerateJSONFile.generateMovementJSONArray(tickEventReplay.get(i).getMoveMentList()));
			jsonArray.put(jsonObject);
		}
		//write data & close stream.
		pw.write(jsonArray.toString());
		pw.flush();
		pw.close();
	}
	
	//replay load method entry
	@Override
	public void loadFlightPathsForReplay(InputStream replayStream) {
		//clear old data , need to be checked 
		this.airportList.clear();
		this.planeList.clear();
		this.trafficList.clear();
		airProxIncidentList.clear();
		tickEventReplay.clear();
		simtimeTicks = 0; 
		//switch mode
		this.mode = 1;
		this.record = 0; //record off
		ReplayJsonLoader jsonLoader = new ReplayJsonLoader(replayStream);
		jsonLoader.initializeJsonData();
		tickEventReplay = jsonLoader.getReplayEventList();
		
	}
	//loader new Simulation file
	@Override
	public void loadSimulation(ISimulationLoader loader) {
		this.airportList.clear();
		this.planeList.clear();
		this.trafficList.clear();
		airProxIncidentList.clear();
		tickEventReplay.clear();
		simtimeTicks = 0;
//		threadStatus = true;
		mode = 0; //normalMode;
		record = 0; //record off
		startRecordTime = -1;
		stopRecordTime = -1;		
		this.loadAirports(loader);
		this.loadTraffic(loader);
		
	}
	@Override
	public void loadTraffic(ISimulationLoader loader) {
		//copy traffic data to planeList
		if(mode == 0) {  //Simulation mode is normal mode, 
			while(loader.hasNextTraffic()) {
				Traffic traffic = loader.nextTraffic();
				//add traffic plane to planeList
				Airport origin = (Airport)getAirport(traffic.getOrigin());
				Airport destination = (Airport)getAirport(traffic.getDestination());
				planeList.add(new Plane(traffic.getCallsign(),traffic.getLocation(), origin, destination));

				//Update Airport planeNumber.
				if(airportList != null) {
					for(int i = 0 ; i < airportList.size() ; i++) {
						Airport airport = (Airport)airportList.get(i);
						if(airport.getCode().equals(traffic.getOrigin()))  { // find this airport, and update planeNumber
							airport.setPlaneNumber(Integer.parseInt((traffic.getCallsign().split("_"))[1]));
						}
					}
				}
				trafficList.add(traffic);
			}
			
		}else {  //Simulation mode is replay mode, data is stored in tickEventReplay ArrayList
			if(tickEventReplay == null) {
				tickEventReplay = ((SimulationJSONLoader)loader).getReplayEventList();  //拿到tickEventReplay的所有数据
			}
		}
	}
	@Override
	public void loadAirports(ISimulationLoader loader) {
		if(mode ==0) { 
			while(loader.hasNextAiport()) {
				airportList.add(loader.nextAirport());
			}
				
		}else {  // Simulation mode = 1, replay mode, load data from jsonFile, all data is stored in tickEventReplay ArrayList
			if(tickEventReplay == null) {
				tickEventReplay = ((SimulationJSONLoader)loader).getReplayEventList();  //get tickEventReplay data
			}
		}
	}
	
	@Override
	public void setLogFile(ISimulationLogger log) {
		for(AirproxIncidentUtil airProxIncident: airProxIncidentList) {
			log.writeLogEntry(airProxIncident.getCallSignPlane1(), airProxIncident.getCallSignPlane2(), airProxIncident.getPosition(), airProxIncident.getSimulationTime());
			
		}
		((TextLogger)log).close();
	}
	
	@Override
	public void addPlane(String callsign, String startAirport, String destinationAirport) {	
		int flag1 = 0, flag2 = 0;
		Airport origin = null, destination = null;
		for(IAirport i: airportList) {
			if(i.getCode().equals(startAirport) == true) {
				flag1 = 1;
				origin = (Airport)i;
			}
			if(i.getCode().equals(destinationAirport) == true) {
				flag2 = 1;
				destination = (Airport)i;
			}
		}
		if(flag1 ==0 || flag2 ==0) {
			throw new SimulationException("either airport does not exist!");
		}
		for(IPlane i: planeList) {
			if(i.getName().equals(callsign)) {
				throw new SimulationException("a with the same call sign already exists!");
			}
		}
		Plane plane= new Plane(callsign, origin, destination);
		plane.setStatus(2); //once create, then take off
		planeList.add(plane);
		
		
		//add to planeCreateList so that the GUI class could know what has changed
		planeCreateList.add(plane);
		
		if(this.record == 1) {  //record on
			tickEventReplay.get(simtimeTicks-startRecordTime).getPlaneList().add(plane);
			tickEventReplay.get(simtimeTicks-startRecordTime).getPlaneCreateList().add(plane);
		}		
	}

	@Override
	public List<IPlane> getPlanes() {
		if(mode == 0) {
			return planeList;
		}else {
			return tickEventReplay.get(simtimeTicks).getPlaneList();
		}
		
	}

	@Override
	public IPlane getPlane(String callSign) {
		if(mode == 0) {
			for(IPlane i: planeList) {
				if(i.getName().equals(callSign)) {
					return i;
				}
			}
		}
		if(mode == 1){
			for(IPlane i: tickEventReplay.get(simtimeTicks).getPlaneList()) {
				if(i.getName().equals(callSign))
					return i;
			}
		}	
		throw new SimulationException("the named plane does not exist in the simulation!");
		
	}

	@Override
	public List<IAirport> getAirports() {
		if(mode == 0)
			return airportList;
		else return tickEventReplay.get(simtimeTicks - startRecordTime).getAirportList();
	}

	@Override
	public IAirport getAirport(String code) {
		if(mode == 0) {
			for(IAirport i: airportList) {
				if(i.getCode().equals(code)) {
					return i;
				}
			}
		}
		if(mode == 1) {
			for(IAirport i: tickEventReplay.get(simtimeTicks - startRecordTime).getAirportList()) {
				if(i.getCode().equals(code)) {
					return i;
				}
			}
		}
		
		throw new SimulationException("the named airport does not exists in the simulation!");
	}
	
	@Override
	public void addAirport(String code, String name, GridLocation location) {
		if(mode == 0) {
			for(int i = 0 ; i < airportList.size() ; i++) {
				if(airportList.get(i).getCode().equals(code))
					throw new SimulationException("this airport has exist in the simulation!");
			}
			
			Airport airport = new Airport(code,name,location);
			airportList.add(new Airport(code,name, location));
			
			if(this.record == 1) { //record on
				//only need to add create Airport to AirportCreateList, because during every tick end, it will add airportList.
				tickEventReplay.get(simtimeTicks - startRecordTime).getAirportCreateList().add(airport);
			}
		}else {  //replay mode
			 for(IAirport airport: tickEventReplay.get(simtimeTicks - startRecordTime).getAirportCreateList()) {
				 System.out.println(airport.toString() + "  --> has been created !");
			 }
		}
	}
	
	@Override
	public void addSimulationListener(SimulationListener listener) {
		this.simulationListener = listener;
	}
	@Override
	public void removeSimulationListener(SimulationListener listener) {
		this.simulationListener = null;
	}
	
	public List<Traffic> getTrafficList() {
		return this.trafficList;
	}
	public int getMode() {
		return this.mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public List<IPlane> getPlaneCreateList(){
		if(mode == 0) {
			return this.planeCreateList;
		}
		if(mode == 1) {
			return this.tickEventReplay.get(simtimeTicks).getPlaneCreateList();
		}
		return null;
	}
	
	
	public List<TickEventReplay> getTickEventReplayFile() {
		return this.tickEventReplay;
	}
	
	public boolean getSimThreadStatus() {
		return this.threadStatus;
	}
	
	public void setRecordStatus(int flag) {
		this.record = flag;
		this.startRecordTime = simtimeTicks;
	}
	
	public int getRecordStatus() {
		return this.record;
	}
	
	public void setWriteRecordFileSignal(int flag) {
		this.writeReplayFileSignal = flag;
		System.out.println("record stopped");
	}
	
	public void setSimTicks(int simtick) {
		this.simtimeTicks = simtick;
	}
}
