package japrc2017.io;

import java.util.NoSuchElementException;

import japrc2017.IAirport;
import japrc2017.ISimulation;
import japrc2017.Traffic;

/**
 * A ISimulationLoader is an object that allows to load a simulation from a
 * source (text file or other). A {@link ISimulation} object can read the
 * content of the loader via the methods nextAirport() and nextTraffic(). An
 * object such as a {@link ISimulation} should iterate through all the
 * {@link IAirport} in the loader using the method nextAirport(), and must check
 * beforehand if such an element exists using the method hasNextAirport().
 * Similarly, the object  should iterate through all the
 * {@link Traffic} in the loader using the method nextTraffic(), and must check
 * beforehand if such an element exists using the method hasNextTraffic().
 * 
 * 
 * @author Lilian
 *
 */
public interface ISimulationLoader {

	/**
	 * 
	 * @return true if the simulation loader contains another {@link IAirport}
	 *         entry, false otherwise
	 */
	public boolean hasNextAiport();

	/**
	 * 
	 * @return true if the simulation loader contains another {@link Traffic} entry,
	 *         false otherwise
	 */
	public boolean hasNextTraffic();

	/**
	 * 
	 * @return the next {@link IAirport} from the loader
	 * @throws NoSuchElementException
	 *             if there is no remaining {@link IAirport}
	 */
	public IAirport nextAirport();

	/**
	 * 
	 * @return the next {@link Traffic} from the loader
	 * @throws NoSuchElementException
	 *             if there is no remaining {@link Traffic}
	 */
	public Traffic nextTraffic();

}
