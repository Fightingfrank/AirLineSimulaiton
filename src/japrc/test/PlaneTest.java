package japrc.test;
/*
 * ExamNumber : Y3851551
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.Plane;

public class PlaneTest {
	
	private static Plane plane1;
	private static Plane plane2;
	private static Plane plane3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		IAirport airport1 = new Airport("YK","York", new GridLocation(30,50));
		IAirport airport2 = new Airport("MAN","Manchester", new GridLocation(20,20));
		plane1 = new Plane("LRH_001");
		plane3 = new Plane("LRH_001",new GridLocation(30,50),airport1,airport2);
	}


	@Test
	public void testPlaneString() {
		assertEquals("LRH_001",plane1.getName());
	}


	@Test
	public void testPlaneStringGridLocationStringStringDouble() {
		assertEquals("LRH_001",plane3.getName());
		assertEquals(new GridLocation(30,50),plane3.getLocation());
		assertEquals("York",plane3.getOrigin().getName());
		assertEquals("Manchester",plane3.getDestination().getName());
		assertEquals(1,plane3.getSpeed(),0);
		
	}

	@Test
	public void testGetName() {
		assertEquals("LRH_001",plane1.getName());
	}

	@Test
	public void testGetFlyToDestinationTickCost() {
		assertEquals(0,plane1.getFlyToDestinationTickCost());
	}

	@Test
	public void testSetFlyToDestinationTickCost() {
		plane1.setFlyToDestinationTickCost(2);
		assertEquals(2,plane1.getFlyToDestinationTickCost());
		
	}

	@Test
	public void testGetStatus() {
		assertEquals(2,plane1.getStatus());
	}

	@Test
	public void testSetStatus() {
		plane1.setStatus(2);
		assertEquals(2,plane1.getStatus());
	}

	@Test
	public void testGetLocation() {
		assertEquals(new GridLocation(30,50),plane3.getLocation());
	}

	@Test
	public void testSetLocation() {
		plane1.setLocation(new GridLocation(40,50));
		assertEquals(new GridLocation(40,50),plane1.getLocation());
	}

	@Test
	public void testGetOrigin() {
		assertEquals("York",plane3.getOrigin().getName());
	}

	@Test
	public void testGetDestination() {
		assertEquals("Manchester",plane3.getDestination().getName());
	}

	@Test
	public void testSetSpeed() {
		plane1.setSpeed(2.1);
		assertEquals(2.1,plane1.getSpeed(),0);
	}

	@Test
	public void testGetSpeed() {
		assertEquals(1,plane1.getSpeed(),0);
	}
	
	

}
