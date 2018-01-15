package japrc2017.io;

import japrc2017.GridLocation;

public interface ISimulationLogger {

	/**
	 * Write an Airprox-incident entry to the Log file.
	 * 
	 * @param callSignPlane1
	 *            the callsign of one of the plane involved in the airprox-incident
	 * @param callsignPlane2
	 *            he callsign of the other plane involved in the airprox-incident
	 * @param position
	 *            the location where the incident took place
	 * @param simulationTime
	 *            the simulation time at which the incident occurred.
	 */
	public void writeLogEntry(String callSignPlane1, String callsignPlane2, GridLocation position, long simulationTime);

}
