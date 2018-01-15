package japrc2017.event;

public interface SimulationListener {

	/**
	 * Tells a simulation listener that something about the simulation has changed
	 * (and hence the GUI may need to redraw itself)
	 * 
	 * @param event
	 *            event containing the source (i.e. simulation) that has changed.
	 */
	public void notifySimHasChanged(SimulationEvent event);
}
