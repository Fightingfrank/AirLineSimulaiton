package japrc2017.util;

/*
 * ExamNumber : Y3851551
 */

import japrc2017.GridLocation;

public class AirproxIncidentUtil {
	private String callSignPlane1;
	private String callSignPlane2;
	private GridLocation position;
	private int simulationTime;
	
	public AirproxIncidentUtil(String call1, String call2, GridLocation position, int simTime) {
		this.callSignPlane1 = call1;
		this.callSignPlane2 = call2;
		this.position = position;
		this.simulationTime = simTime;
	}
	
	public String getCallSignPlane1() {
		return callSignPlane1;
	}

	public void setCallSignPlane1(String callSignPlane1) {
		this.callSignPlane1 = callSignPlane1;
	}

	public String getCallSignPlane2() {
		return callSignPlane2;
	}

	public void setCallSignPlane2(String callSignPlane2) {
		this.callSignPlane2 = callSignPlane2;
	}

	public GridLocation getPosition() {
		return position;
	}

	public void setPosition(GridLocation position) {
		this.position = position;
	}

	public int getSimulationTime() {
		return simulationTime;
	}

	public void setSimulationTime(int simulationTime) {
		this.simulationTime = simulationTime;
	}
}
