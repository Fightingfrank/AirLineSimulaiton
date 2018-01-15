package japrc2017;

import java.util.List;

public interface ISimulation {
	/**
	 * Adds a plane to the simulation.
	 * 
	 * @param callsign
	 *            The callsign of the plane to be added
	 * @param startAirport
	 *            The IATA code for the airport the plane took-off from
	 * @param destinationAirport
	 *            The IATA code for the airport the plane is flying to
	 * @throws SimulationException
	 *             if either airport does not exist
	 * @throws SimulationException
	 *             if a plane with the same call sign already exists
	 */
	public void addPlane(String callsign, String startAirport, String destinationAirport);

	/**
	 * @return a list of the planes in the simulation
	 * 
	 */
	public List<IPlane> getPlanes();

	/**
	 * Returns the object for a specific named plane.
	 * 
	 * @param callSign
	 *            the name of the plane
	 * @return The named plane
	 * @throws SimulationException
	 *             if the named plane does not exist in the simulation
	 */
	public IPlane getPlane(String callSign);

	/**
	 * Adds an airport to the simulation.
	 * 
	 * @param code
	 *            The IATA code for the airport
	 * @param name
	 *            The full name of the airport
	 * @param location
	 *            The grid location of the airport
	 */
	public void addAirport(String code, String name, GridLocation location);

	/**
	 * @return a list of the airports in the simulation
	 * 
	 */
	public List<IAirport> getAirports();

	/**
	 * Returns the object for a specific named airport.
	 * 
	 * @param code
	 *            the IATA code for the airport
	 * @return The named airport
	 * @throws SimulationException
	 *             if the named airport does not exist in the simulation
	 */
	public IAirport getAirport(String code);

	/**
	 * Tells the simulator to move simulated time forward by one tick, and take any
	 * actions that should occur in that time.
	 */
	public void tick();

	/**
	 * @return the number of ticks since the simulation was started
	 */
	public int getSimTime();

	/**
	 * Gets the dimensions of the simulated world, in grid squares.
	 * 
	 * @return the dimensions of the world
	 */
	public GridLocation getMapDimensions();

	
	/**
	 * Sets the dimensions of the simulated world, in grid squares.
	 * 
	 * @param width the width of the map
	 * @param height the height of the map
	 */
	public void setMapDimensions(int width, int height);

	/**
	 * Moves the specified plane to the specified location, provided that the
	 * specified move is legal. If the move is not legal the method returns with no
	 * effect.
	 * 
	 * @param planeName
	 *            the name of the plane to move
	 * @param newLoc
	 *            the location to move the plane to
	 */
	public void movePlaneTo(String planeName, GridLocation newLoc);

	/**
	 * Starts the simulator running continuously, at a rate determined by the most
	 * recent call to setTickDelay(), and returns. If the simulator is already
	 * running, it does nothing.
	 */
	public void start();

	/**
	 * If the simulator is currently running continuously (after a start() command),
	 * this stops it running. If the simulator is not running, does nothing.
	 */
	public void pause();

	/**
	 * Sets the delay between ticks when the simulation is running continuously
	 * (after a start() command).
	 * 
	 * @param millis
	 *            The real-time delay between ticks, in milliseconds
	 */
	public void setTickDelay(int millis);

}
