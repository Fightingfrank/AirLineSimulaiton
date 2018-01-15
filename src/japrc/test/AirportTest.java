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

public class AirportTest {
	
	private static Airport airport1;
	private static Airport airport2; 

	@Before
	public void setUp() throws Exception {
		airport1 = new Airport("YK","York",new GridLocation(30,50));
		airport2 = new Airport("YK");
	}

	@Test
	public void testGetCode() {
		assertEquals("YK",airport1.getCode());
	}

	@Test
	public void testGetName() {
		assertEquals("York",airport1.getName());
	}

	@Test
	public void testGetPlaneNumber() {
		assertEquals(1,airport1.getPlaneNumber());
		assertEquals(1,airport2.getPlaneNumber());
	}

	@Test
	public void testSetPlaneNumber() {
		airport1.setPlaneNumber(2);
		assertEquals(2,airport1.getPlaneNumber());
	}

	@Test
	public void testGetLocation() {
		assertEquals(new GridLocation(30,50),airport1.getLocation());
	}

	@Test
	public void testGetOpen() {
		assertEquals(false,airport1.getOpen());
	}

	@Test
	public void testSetTakeOffProbability() {
		airport1.setTakeOffProbability(0.2);
		assertEquals(0.2,airport1.getTakeOffProbability(),0);
	}

	@Test
	public void testGetTakeOffProbability() {
		assertEquals(0,airport1.getTakeOffProbability(),0);
	}
	
	@Test
	public void testGetStatus() {
		assertEquals(false,airport1.getStatus());
	}
	
	@Test
	public void testSetStatus() {
		airport1.setStatus(true);
		assertEquals(true,airport1.getStatus());
	}
	
	@Test
	public void testToString() {
		assertEquals("York(YK) -30,50",airport1.toString());
	}
	


}
