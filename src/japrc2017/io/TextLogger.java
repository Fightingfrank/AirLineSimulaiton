package japrc2017.io;

/*
 * ExamNumber : Y3851551
 */

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

import japrc2017.GridLocation;
import japrc2017.util.AirproxIncidentUtil;

public class TextLogger implements ISimulationLogger{
	
	private PrintWriter pw;
	
	public TextLogger (OutputStream stream) {
		this.pw = new PrintWriter(stream);
	}
	@Override
	public void writeLogEntry(String callSignPlane1, String callsignPlane2, GridLocation position,
			long simulationTime) {
		pw.println(callSignPlane1 + "," + callsignPlane2 + "," + position.getX() + "," + position.getY() + "," + simulationTime);
		pw.flush();
	}
	
	public void close() {
		if(pw!=null) {
			pw.close();
		}
	}

}
