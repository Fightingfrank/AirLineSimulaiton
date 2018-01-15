package japrc2017;

/**
 * Class representing a traffic entry (i.e. a plane traffic information). A traffic entry contains:
 * - the callsign of a plane, 
 * - the plane's airport of origin
 * - the plane destination,
 * - the plane current location
 * 
 * @author Lilian
 *
 */
public final class Traffic {

	/**
	 * The callsign of the plane
	 */
	private final String callsign; 
	
	/**
	 * The IATA code of the destination airport.
	 */
	private final String destination;
	
	/**
	 * the IATA code of the plane's airport of origin.
	 */
	private final String origin;
	
	/**
	 * The current position of the plane on the grid.
	 */
	private GridLocation planePosition;

	/**
	 * 
	 * @param callsign the callsign of the plane
	 * @param origin the IATA code of the plane's airport of origin
	 * @param destination the IATA code of the plane's destination airport
	 * @param planePosition the current location of the plane on the grid.
	 */
	public Traffic(String callsign, String origin, String destination, GridLocation planePosition) {
		this.callsign = callsign;
		this.origin = origin;
		this.destination = destination;
		this.planePosition = planePosition;
	}
	
	/**
	 * 
	 * @return the callsign of the plane.
	 */
	public String getCallsign() {
		return callsign;
	}
	
	/**
	 * 
	 * @return the IATA code of the destination airport
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * 
	 * @return the IATA code of the origin (departure) airport
	 */
	public String getOrigin() {
		return origin;
	}
	
	/**
	 * 
	 * @return the current location of the plane.
	 */
	public GridLocation getLocation() {
		return planePosition;
	}
	
	/**
	 * set the plane position to location.
	 * @param location the new position of the plane.
	 * @return the previous location of the plane.
	 */
	public GridLocation moveTo(GridLocation location) {
		GridLocation oldLocation = planePosition;
		planePosition = location;
		return oldLocation;
	}
	
	@Override
	public boolean equals(Object o) {
        if (o == this){
            return true;
        } else {
            if (o instanceof Traffic){
            	Traffic traffic = (Traffic) o;
            	if (this.planePosition.equals(traffic.planePosition) && 
            			this.callsign.equals(traffic.callsign) &&
            			this.destination.equals(traffic.destination) &&
                		this.origin.equals(traffic.origin)){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
	}

    @Override
    public String toString()
    {
        return ("(" + callsign + ": " + origin + 
        		"-->" + destination + "@ "+ planePosition +")");
    }

}
