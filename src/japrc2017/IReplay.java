package japrc2017;

import java.io.InputStream;
import java.io.OutputStream;

public interface IReplay {

	/**
	 * Tells the {@link ISimulation} to log all observable flight events to the
	 * supplied stream, ready for later replay.
	 * 
	 * @param replayStream
	 *            The stream to be logged to
	 */
	public void setReplayLogFile(OutputStream replayStream);

	/**
	 * Tells the {@link ISimulation} to switch to replay mode, and load a set of
	 * flight events from the supplied stream, ready to replay them.
	 * 
	 * @param replayStream
	 *            The stream to load the events from
	 */
	public void loadFlightPathsForReplay(InputStream replayStream);

}
