package japrc.test;

/*
 * ExamNumber : Y3851551
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.Traffic;
import japrc2017.io.SimulationTextLoader;
import japrc2017.util.SimulationUtil;

public class SimulationTextLoaderTest {
	
	private static SimulationTextLoader simulationTextLoader;
	
	@Before
	public void startUp() throws Exception{
		String trafficFilePath = SimulationUtil.getFilePath("traffic.txt");
		String airportFilePath = SimulationUtil.getFilePath("airports.txt");
		InputStream airports = new FileInputStream(airportFilePath);
		InputStream traffic = new FileInputStream(trafficFilePath);
		simulationTextLoader = new SimulationTextLoader(airports, traffic);
		simulationTextLoader.initializeInputStream();
	}
	@After
	public void after() throws Exception{
		simulationTextLoader.closeAll();
	}
	
	@Test
	public void testHasNextAiport() {
		
		for(int i = 0 ; i < 6 ; i++ ) {
			assertEquals(true, simulationTextLoader.hasNextAiport());
		}
		assertEquals(false, simulationTextLoader.hasNextAiport());
	}

	@Test
	public void testHasNextTraffic() {
		for(int i = 0 ; i < 4 ; i++ ) {
			assertEquals(true, simulationTextLoader.hasNextTraffic());
		}
		assertEquals(false, simulationTextLoader.hasNextTraffic());
	}

	@Test
	public void testNextAirport() {
		Airport testAirport = new Airport("LHR","London Heathrow",new GridLocation(32,55), 0.01);
		if(simulationTextLoader.hasNextAiport()) {
			assertEquals(testAirport.getCode(),simulationTextLoader.nextAirport().getCode());
		}
		
	}

	@Test
	public void testNextTraffic() {
		Traffic testTraffic = new Traffic("LHR_001","LHR","TXL", new GridLocation(35,55));
		if(simulationTextLoader.hasNextTraffic()) {
			assertEquals(testTraffic.getCallsign(),simulationTextLoader.nextTraffic().getCallsign());
		}
		
	}

}
