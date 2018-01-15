package japrc2017;

public interface IAirport {
	/**
	 * Returns the IATA code of the airport (e.g. "LHR")
	 * 
	 * @return the IATA code of the plane
	 */
	public String getCode();

	/**
	 * Returns the full name of the airport (e.g. "London Heathrow")
	 * 
	 * @return the full name of the airport
	 */
	public String getName();

	/**
	 * Returns the grid location of the airport
	 * 
	 * @return the grid location of the airport
	 */
	public GridLocation getLocation();

	/**
	 * Returns a boolean indicating whether the airport is open or not
	 * 
	 * @return a true indicating the airport is open, false otherwise.
	 */
	public boolean getOpen();

	/**
	 * Tells the airport that one tick of simulation time has passed
	 * 
	 */
	public void tick();

	/**
	 * Sets the probability per tick that a new plane will take-off from this airport
	 * (except at night, when no planes may take-off)
	 * 
	 * @param prob
	 *            the probability per tick that a new plane will take-off from this
	 *            airport
	 * 
	 */
	public void setTakeOffProbability(double prob);

	/**
	 * 
	 * @return the probability per tick that a new plane will take-off from this
	 *         airport (except at night, when no planes may take-off)
	 */
	public double getTakeOffProbability();
}
