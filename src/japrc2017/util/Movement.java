package japrc2017.util;


/*
 * ExamNumber : Y3851551
 */

import japrc2017.GridLocation;

public class Movement {

	private String callsign;
	private GridLocation oldLocation;
	private GridLocation newLocation;
	private String originalAirport;
	private String destinationAirport;
	
	public Movement(String callsign, GridLocation oldLocation, GridLocation newLocation, String originalAirport,
			String destinationAirport) {
		super();
		this.callsign = callsign;
		this.oldLocation = oldLocation;
		this.newLocation = newLocation;
		this.originalAirport = originalAirport;
		this.destinationAirport = destinationAirport;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public GridLocation getNewLocation() {
		return newLocation;
	}
	public void setNewLocation(GridLocation newLocation) {
		this.newLocation = newLocation;
	}
	public String getDestinationAirport() {
		return destinationAirport;
	}
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	public GridLocation getOldLocation() {
		return oldLocation;
	}
	public void setOldLocation(GridLocation oldLocation) {
		this.oldLocation = oldLocation;
	}
	public String getOriginalAirport() {
		return originalAirport;
	}
	public void setOriginalAirport(String originalAirport) {
		this.originalAirport = originalAirport;
	}
}
