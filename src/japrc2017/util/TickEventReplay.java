package japrc2017.util;

/*
 * ExamNumber : Y3851551
 */

import java.util.ArrayList;
import java.util.List;

import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Traffic;

public class TickEventReplay {
	
	public List<IAirport> airportList;
	public List<IAirport> airportCreateList;
	public List<IPlane> planeCreateList;
	public List<IPlane> planeLandList;
	public List<IPlane> planeList;
	public List<Traffic> trafficList;
	public List<Movement> moveMentList;
	
	public TickEventReplay() {
		airportList = new ArrayList<IAirport>();
		airportCreateList = new ArrayList<IAirport>();
		planeCreateList = new ArrayList<IPlane>();
		planeLandList = new ArrayList<IPlane>();
		planeList = new ArrayList<IPlane>();
		trafficList = new ArrayList<Traffic>();
		moveMentList = new ArrayList<Movement>();
		
	}
	public List<IPlane> getPlaneLandList() {
		return planeLandList;
	}
	public void setPlaneLandList(List<IPlane> planeLandList) {
		this.planeLandList = planeLandList;
	}
	public List<IAirport> getAirportList() {
		return airportList;
	}
	public void setAirportList(List<IAirport> airportList) {
		this.airportList = airportList;
	}
	public List<IAirport> getAirportCreateList() {
		return airportCreateList;
	}
	public void setAirportCreateList(List<IAirport> airportCreateList) {
		this.airportCreateList = airportCreateList;
	}
	public List<IPlane> getPlaneCreateList() {
		return planeCreateList;
	}
	public void setPlaneCreateList(List<IPlane> planeCreateList) {
		this.planeCreateList = planeCreateList;
	}
	public List<IPlane> getPlaneList() {
		return planeList;
	}
	public void setPlaneList(List<IPlane> planeList) {
		this.planeList = planeList;
	}
	public List<Traffic> getTrafficList() {
		return trafficList;
	}
	public void setTrafficList(List<Traffic> trafficList) {
		this.trafficList = trafficList;
	}
	public List<Movement> getMoveMentList() {
		return moveMentList;
	}
	public void setMoveMentList(List<Movement> movementList) {
		this.moveMentList = movementList;
	}
}
