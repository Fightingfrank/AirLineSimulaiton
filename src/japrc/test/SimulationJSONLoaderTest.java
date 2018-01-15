package japrc.test;

/*
 * ExamNumber : Y3851551
 */

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.Traffic;
import japrc2017.io.SimulationJSONLoader;
import japrc2017.io.SimulationTextLoader;
import japrc2017.util.SimulationUtil;

public class SimulationJSONLoaderTest {

	
	private static SimulationJSONLoader loader;
	

	@Before
	public void setUp() throws Exception {
		String path = SimulationUtil.getFilePath("simulationData.json");
		InputStream jsonStream = new FileInputStream(path);
		loader = new SimulationJSONLoader(jsonStream);
		loader.initializeJsonData();
	}
	

	@After
	public void tearDown() throws Exception {
		loader.closeAll();
	}

	@Test
	public void testHasNextAiport() {
		for(int i = 0 ; i < 6 ; i++ ) {
			assertEquals(true, loader.hasNextAiport());
			loader.nextAirport();
		}
		assertEquals(false, loader.hasNextAiport());
	}

	@Test
	public void testHasNextTraffic() {
		for(int i = 0 ; i < 4 ; i++ ) {
			assertEquals(true, loader.hasNextTraffic());
			loader.nextTraffic();
		}
		assertEquals(false, loader.hasNextTraffic());
	}

	@Test
	public void testNextAirport() {
		Airport testAirport = new Airport("LHR","London Heathrow",new GridLocation(32,55), 0.01);
		if(loader.hasNextAiport()) {
			assertEquals(testAirport.getCode(),loader.nextAirport().getCode());
		}
	}

	@Test
	public void testNextTraffic() {
		Traffic testTraffic = new Traffic("LHR_001","LHR","TXL", new GridLocation(35,55));
		if(loader.hasNextTraffic()) {
			assertEquals(testTraffic.getCallsign(),loader.nextTraffic().getCallsign());
		}
	}

}
