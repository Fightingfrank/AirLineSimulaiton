package japrc.test;

/*
 * ExamNumber : Y3851551
 */

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import japrc2017.ATMSimulation;
import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Plane;
import japrc2017.SimulationException;
import japrc2017.UI.UserInterface;
import japrc2017.util.SimulationUtil;

public class ATMSimulationTest {
	
	ATMSimulation atmSimulation1;
	ATMSimulation atmSimulation2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		atmSimulation1 = new ATMSimulation();
		atmSimulation2 = new ATMSimulation(new GridLocation(100,200));
		atmSimulation1.addAirport("LHR", "Manchester", new GridLocation(30,50));
		atmSimulation1.addAirport("YK", "York", new GridLocation(40,20));
		
		atmSimulation2.addAirport("LHR", "Manchester", new GridLocation(30,50));
		atmSimulation2.addAirport("YK", "York", new GridLocation(40,20));
		
		atmSimulation1.addPlane("YK_001", "YK", "LHR");
		atmSimulation1.addPlane("LHR_001", "LHR", "YK");
		
		atmSimulation2.addPlane("YK_001", "YK", "LHR");
		atmSimulation2.addPlane("LHR_001", "LHR", "YK");
		
		atmSimulation1.setMapDimensions(100, 100);
		atmSimulation2.setMapDimensions(100, 100);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testATMSimulation() {
		assertNotNull(atmSimulation2);
	}
	
	
	@Test(expected= SimulationException.class) 
	public void testAddPlane() {
		//test airport does not exits
		
		
		atmSimulation2.addPlane("LHR_001", "SAD", "LHR"); //startAirport does not exit
		atmSimulation2.addPlane("LHR_001", "LHR", "SAD"); //destination Airport does not exit
		atmSimulation2.addPlane("LHR_001", "SAD", "asd"); //both of the start and destination airport does not exit	
		atmSimulation2.addPlane("YK_001", "YK", "LHR");
		
		assertNotNull(atmSimulation2.getPlane("YK_001"));
		
	}

	@Test
	public void testGetPlanes() {
		List<IPlane> testPlaneList = new ArrayList<IPlane> ();

		
		testPlaneList.add(new Plane("YK_001",new Airport("YK","York", new GridLocation(40,20)),new Airport("LHR","Manchester", new GridLocation(30,50))));
		testPlaneList.add(new Plane("LHR_001",new Airport("LHR","Manchester", new GridLocation(30,50)),new Airport("YK","York",new GridLocation(40,20))));
		

		assertEquals(true,atmSimulation1.getPlanes().equals(testPlaneList));
	}

	@Test
	public void testGetPlane() {
		assertEquals("LHR_001",atmSimulation2.getPlane("LHR_001").getName());
	}

	@Test
	public void testAddAirport() {
		atmSimulation1.addAirport("LD", "Leeds", new GridLocation(10,20));
		assertNotNull(atmSimulation1.getAirport("LD"));
	}

	@Test
	public void testGetAirports() {
		
		List<IAirport> testAirport = new ArrayList<IAirport> ();
		testAirport.add(new Airport("LHR", "Manchester", new GridLocation(30,50)));
		testAirport.add(new Airport("YK", "York", new GridLocation(40,20)));
		for(int i = 0 ; i < 2; i++) {
			assertEquals(testAirport.get(i).getCode(),atmSimulation2.getAirports().get(i).getCode());
		}
	}

	@Test
	public void testGetAirport() {
		assertEquals("YK",((Airport)atmSimulation1.getAirport("YK")).getCode());
	}
	
	

	@Test
	public void testGetSimTime() {
		assertEquals(0, atmSimulation1.getSimTime());
	}

	@Test
	public void testGetMapDimensions() {
		assertEquals(new GridLocation(100,100),atmSimulation1.getMapDimensions());
	}

	@Test
	public void testSetMapDimensions() {
		atmSimulation1.setMapDimensions(20, 20);
		assertEquals(new GridLocation(20,20),atmSimulation1.getMapDimensions());
	}

	@Test
	public void testMovePlaneTo() {
		Plane testPlane = (Plane)atmSimulation1.getPlane("LHR_001");
		atmSimulation1.movePlaneTo(testPlane.getName(), new GridLocation(20,20));
		
		
		assertEquals(new GridLocation(20,20),testPlane.getLocation());
		
		
	}
	@Test
	public void testSetTickDelay() {
		atmSimulation1.setTickDelay(10);
		assertEquals(10,atmSimulation1.getTickDelay());
		
	}
	
	
	@Test
	public void testTick() {
		atmSimulation2.getAirport("YK").setTakeOffProbability(100);
		atmSimulation2.getAirport("LHR").setTakeOffProbability(0);
		int t = atmSimulation2.getPlanes().size();
		UserInterface ui = new UserInterface();
		atmSimulation2.addSimulationListener(ui);
		atmSimulation2.tick();
		assertEquals(t+1,atmSimulation2.getPlanes().size());
	}
	
	@Test
	public void testLoadFlightPathsForReplay() throws FileNotFoundException {
		String path = SimulationUtil.getFilePath("replayData.json");
		InputStream stream = new FileInputStream(path);
		atmSimulation2.loadFlightPathsForReplay(stream);
		assertEquals("LHR_001",atmSimulation2.getTickEventReplayFile().get(0).getPlaneList().get(0).getName());
	}
	
	@Test
	public void testCheckPlaneLand() {
		atmSimulation2.addAirport("ctr","LONDON", new GridLocation(20,20));
		atmSimulation2.addPlane("LHR_007", "LHR","ctr" );
		((Plane)atmSimulation2.getPlane("LHR_007")).setLocation(new GridLocation(20,20));
		
		atmSimulation2.checkPlaneLand();
		
		assertEquals(3,((Plane)atmSimulation2.getPlane("LHR_007")).getStatus());
	}

	
	@Test
	public void testPlaneTakeoff() {
		atmSimulation2.getAirport("YK").setTakeOffProbability(100);
		int t = atmSimulation2.getPlanes().size();
		atmSimulation2.planeTakeoff();
		assertEquals(t+1,atmSimulation2.getPlanes().size());
	}
	
	@Test
	public void testStart() {
		atmSimulation2.start();
		assertEquals(true,atmSimulation2.getSimThreadStatus());
		atmSimulation2.pause();
		assertEquals(false,atmSimulation2.getSimThreadStatus());
		atmSimulation2.start();
		assertEquals(true,atmSimulation2.getSimThreadStatus());
	}
	
	@Test
	public void testPause() {
		atmSimulation2.start();
		assertEquals(true,atmSimulation2.getSimThreadStatus());
		atmSimulation2.pause();
		assertEquals(false,atmSimulation2.getSimThreadStatus());
		atmSimulation2.start();
		assertEquals(true,atmSimulation2.getSimThreadStatus());
	}


}
