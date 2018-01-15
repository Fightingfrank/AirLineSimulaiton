package japrc2017.event;

import japrc2017.ISimulation;

/**
 * An event containing the source of the event. Signal that the source has been
 * modified.
 * 
 * @author Lilian
 *
 */
public class SimulationEvent {

	ISimulation source;

	public SimulationEvent(ISimulation source) {
		this.source = source;
	}

	public ISimulation getSource() {
		return source;
	}

}
