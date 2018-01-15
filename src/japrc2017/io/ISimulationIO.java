package japrc2017.io;

public interface ISimulationIO {
	/**
	 * Tells the simulation to discard the current airports, planes, and traffic in
	 * the simulation, and load a new set from the supplied loader.
	 * 
	 * @param loader
	 *            A {@link ISimulationLoader} containing data about a new set of planes, and
	 *            traffic.
	 */
	public void loadTraffic(ISimulationLoader loader);

	/**
	 * Tells the simulation to discard the current airports the simulation, and load
	 * a new set from the supplied loader.
	 * 
	 * @param loader
	 *            A {@link ISimulationLoader} containing data about a new set of airports.
	 */
	public void loadAirports(ISimulationLoader loader);

	/**
	 * Tells the simulation to discard the current airports, planes, and traffic in
	 * the simulation, and load a new set from the supplied loader.
	 * 
	 * @param loader
	 *            A {@link ISimulationLoader} containing data about a new set of airports,
	 *            planes, and traffic.
	 */
	public void loadSimulation(ISimulationLoader loader);

	/**
	 * Tells the simulation to log all airprox events to the supplied {@link ISimulationLoader}
	 * 
	 * @param log
	 *            The {@link ISimulationLogger} used to log any airprox incident
	 */
	public void setLogFile(ISimulationLogger log);


}
