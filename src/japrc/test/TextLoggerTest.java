package japrc.test;

/*
 * ExamNumber : Y3851551
 */

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import japrc2017.ATMSimulation;
import japrc2017.GridLocation;
import japrc2017.io.TextLogger;
import japrc2017.util.SimulationUtil;

public class TextLoggerTest {
	
	private static TextLogger loger;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		String path = SimulationUtil.getFilePath("Test.txt");
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		loger = new TextLogger(new FileOutputStream(file,true));
		
	
	}

	
	@Test
	public void testWriteLogEntry() throws IOException {
		
		loger.writeLogEntry("1", "2", new GridLocation(20,30), 3);
		loger.close();
		
		String path = SimulationUtil.getFilePath("Test.txt");
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String result ="",line;
		while((line = reader.readLine())!=null) {
			result += line;
		}
		
		String testResult = "1,2,20,30,3";
		
		System.out.println(result);
		assertEquals(result,testResult);
	}

}
