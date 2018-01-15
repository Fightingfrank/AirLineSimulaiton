package japrc2017.event;

public interface ISimulationPublisher {

	/**
	 * Add a SimulationListener object to the list of subscriber of this publisher
	 * of SimulationEvent
	 * 
	 * @param listener the SimulationListener to be added to the list of subscribers
	 */
	public void addSimulationListener(SimulationListener listener);

	/**
	 * Remove the SimulationListener object from the list of subscriber of this publisher
	 * of SimulationEvent
	 * 
	 * @param listener the SimulationListener to be removed from the list of subscribers
	 */
	public void removeSimulationListener(SimulationListener listener);

}
