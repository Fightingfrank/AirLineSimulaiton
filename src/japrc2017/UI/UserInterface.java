package japrc2017.UI;

/*
 * ExamNumber : Y3851551
 */

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import japrc2017.ATMSimulation;
import japrc2017.Airport;
import japrc2017.GridLocation;
import japrc2017.IAirport;
import japrc2017.IPlane;
import japrc2017.Plane;
import japrc2017.Traffic;
import japrc2017.event.SimulationEvent;
import japrc2017.event.SimulationListener;
import japrc2017.io.ISimulationLoader;
import japrc2017.io.SimulationJSONLoader;
import japrc2017.io.SimulationTextLoader;
import japrc2017.util.SimulationUtil;


public class UserInterface implements SimulationListener {
	
	private static final int PLANE_IMAGE_WIDTH = 45;
	private static final int PLANE_IMAGE_HEIGHT = 37;
	private static final int AIRPORT_IMAGE_WIDTH = 25;
	private static final int AIRPORT_IMAGE_HEIGHT = 30;
	private JFrame frame;
	private JLabel jLabelEuropeMap;
	private JTextField jTextFieldNewAirportName;
	private JLabel jLabelNewAirportName;
	private JList jListAirprox;
	private JButton jButtonReplay;
	private JButton jButtonLoadTxtFile;
	private JButton jButtonLoadReplayFile;
	private JButton jButtonRecord;
	private JButton jButtonPause;
	private JButton jButtonStart;
	private JButton jButtonShowPlotCode;
	private JLabel jLabelTime;
	private JList jListDepartures;
	private JLabel jLabelSimulationMode;
	private JLabel jLabelAirportTest;
	private JLabel jLabelTest;
	private JPanel jPanelTest;
	private JButton jButtonLoadJsonData;
	private JPanel jPanelFunctionList;
	private JLabel jLabelFunction;
	private JLabel jLabelNightMode;
	private JButton jButtonAddNewAirport;
	private JTextField jTextFieldGridLocationY;
	private JTextField jTextFieldGridLocationX;
	private JLabel jLabelGridLocation;
	private JTextField jTextFieldNewAirportCode;
	private JLabel jLabelNewAirprotCode;
	private JLabel jLabelNewAirportInformation;
	private JPanel jPanelAddAirport;
	private JList jListArrivals;
	private JList jListAirports;
	private JLabel jLabelAirprox;
	private JScrollPane jScrollPaneAirprox;
	private JScrollPane jScrollPaneDepartures;
	private JLabel jLabelDepartures;
	private JScrollPane jScrollPaneArrivals;
	private JScrollPane jScrollPaneAirport;
	private JLabel jLabelArrivals;
	private JLabel jLabelAirPorts;
	private SimulationEvent simulationEvent;
	private Map<String, PlaneComponent> planeImageMap;
	private Map<String, AirportComponent> airportImageMap;
	private ATMSimulation atmSimulation;
	private Vector<String> airportVector;
	private JPanel mapPanel;
	private int loadDataFlag = 0;  //1 already loaded data
	public UserInterface() {
		super();
		initGUI();
	}
	private void initGUI() {
		try {
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.getContentPane().setForeground(new java.awt.Color(153,153,153));
			{
				jLabelAirPorts = new JLabel();
				frame.getContentPane().add(jLabelAirPorts);
				jLabelAirPorts.setText("Airports");
				jLabelAirPorts.setBounds(572, 58, 93, 15);
				jLabelAirPorts.setBackground(new java.awt.Color(204,225,98));
				jLabelAirPorts.setForeground(new java.awt.Color(102,102,204));
				jLabelAirPorts.setFont(new java.awt.Font("Dialog",0,16));
			}
			
			{
				jLabelEuropeMap = new JLabel();
//				frame.getContentPane().add(jLabelEuropeMap);
				jLabelEuropeMap.setBounds(7, 4, 500, 382);
				ImageIcon image = new ImageIcon(SimulationUtil.getFilePath("europe.png"));
				image.setImage(image.getImage().getScaledInstance(jLabelEuropeMap.getWidth(), jLabelEuropeMap.getHeight(), Image.SCALE_DEFAULT));
				jLabelEuropeMap.setIcon(image);
				jLabelEuropeMap.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				jLabelEuropeMap.setOpaque(false);
				frame.getLayeredPane().add(jLabelEuropeMap,-1);
			}	
			
			{
				jLabelArrivals = new JLabel();
				frame.getContentPane().add(jLabelArrivals);
				jLabelArrivals.setText("Arrivals");
				jLabelArrivals.setBounds(821, 58, 75, 15);
				jLabelArrivals.setForeground(new java.awt.Color(102,102,204));
				jLabelArrivals.setFont(new java.awt.Font("Dialog",0,16));
			}
			{
				jScrollPaneAirport = new JScrollPane();
				frame.getContentPane().add(jScrollPaneAirport);
				jScrollPaneAirport.setBounds(513, 85, 220, 301);
				{
					jListAirports = new JList();
					jScrollPaneAirport.setViewportView(jListAirports);
					//SET Single selection mode
					jListAirports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					jListAirports.addListSelectionListener(new jListAirportsHandle());
					
				}
			}
			{
				jScrollPaneArrivals = new JScrollPane();
				frame.getContentPane().add(jScrollPaneArrivals);
				jScrollPaneArrivals.setBounds(741, 85, 220, 301);
				{
					
					jListArrivals = new JList();
					jListArrivals.addListSelectionListener( (listener) -> {
						String str = (String) jListArrivals.getSelectedValue();
						if(str != null) {
							String planeCode = (str.split(" "))[0];
							if(planeImageMap.get(planeCode)!=null) {
								planeImageMap.get(planeCode).setImagePath("red_plane.jpg");
								for(Entry<String, PlaneComponent> entry: planeImageMap.entrySet()) {
									if(!entry.getKey().equals(planeCode))
										entry.getValue().setImagePath("gray_plane.jpg");
								}
							}
						}
					});
					jScrollPaneArrivals.setViewportView(jListArrivals);
				}
			}
			{
				jLabelDepartures = new JLabel();
				frame.getContentPane().add(jLabelDepartures);
				jLabelDepartures.setText("Departures");
				jLabelDepartures.setBounds(1038, 58, 109, 15);
				jLabelDepartures.setForeground(new java.awt.Color(102,102,204));
				jLabelDepartures.setFont(new java.awt.Font("Dialog",0,16));
			}
			{
				jScrollPaneDepartures = new JScrollPane();
				frame.getContentPane().add(jScrollPaneDepartures);
				jScrollPaneDepartures.setBounds(969, 85, 220, 301);
				{
					jListDepartures = new JList();
					jListDepartures.addListSelectionListener( (listener) -> {
						String str = (String) jListDepartures.getSelectedValue();
						if(str != null) {
							String planeCode = (str.split(" "))[0];
							if(planeImageMap.get(planeCode)!= null) {
								planeImageMap.get(planeCode).setImagePath("red_plane.jpg");
								for(Entry<String, PlaneComponent> entry: planeImageMap.entrySet()) {
									if(!entry.getKey().equals(planeCode))
										entry.getValue().setImagePath("gray_plane.jpg");
								}
							}
						}
					});
					jScrollPaneDepartures.setViewportView(jListDepartures);
				}
			}
			{
				jScrollPaneAirprox = new JScrollPane();
				frame.getContentPane().add(jScrollPaneAirprox);
				jScrollPaneAirprox.setBounds(739, 425, 446, 196);
				{
					jListAirprox = new JList();
					jScrollPaneAirprox.setViewportView(jListAirprox);
				}
			}
			{
				jLabelAirprox = new JLabel();
				frame.getContentPane().add(jLabelAirprox);
				jLabelAirprox.setText("Airprox Accident");
				jLabelAirprox.setBounds(739, 398, 157, 21);
				jLabelAirprox.setFont(new java.awt.Font("Dialog",0,16));
				jLabelAirprox.setForeground(new java.awt.Color(102,102,204));
			}
			{
				jLabelTime = new JLabel();
				frame.getContentPane().add(jLabelTime);
				jLabelTime.setText("19:00 AM");
				jLabelTime.setBounds(808, 12, 110, 24);
				jLabelTime.setFont(new java.awt.Font("Dialog",0,22));
			}
			{
				jPanelAddAirport = new JPanel();
				frame.getContentPane().add(jPanelAddAirport);
				jPanelAddAirport.setBounds(513, 425, 220, 196);
				jPanelAddAirport.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				jPanelAddAirport.setLayout(null);
				{
					jLabelNewAirportName = new JLabel();
					jPanelAddAirport.add(jLabelNewAirportName);
					jLabelNewAirportName.setText("Name:");
					jLabelNewAirportName.setBounds(46, 32, 43, 15);
				}
				{
					jTextFieldNewAirportName = new JTextField();
					jPanelAddAirport.add(jTextFieldNewAirportName);
					jTextFieldNewAirportName.setBounds(97, 29, 111, 22);
				}
				{
					jLabelNewAirprotCode = new JLabel();
					jPanelAddAirport.add(jLabelNewAirprotCode);
					jLabelNewAirprotCode.setText("Code:");
					jLabelNewAirprotCode.setBounds(46, 72, 39, 15);
				}
				{
					jTextFieldNewAirportCode = new JTextField();
					jPanelAddAirport.add(jTextFieldNewAirportCode);
					jTextFieldNewAirportCode.setBounds(97, 69, 111, 22);
				}
				{
					jLabelGridLocation = new JLabel();
					jPanelAddAirport.add(jLabelGridLocation);
					jLabelGridLocation.setText("Location:");
					jLabelGridLocation.setBounds(30, 110, 67, 15);
					jLabelGridLocation.setAlignmentY(0.0f);
				}
				{
					jTextFieldGridLocationX = new JTextField();
					jPanelAddAirport.add(jTextFieldGridLocationX);
					jTextFieldGridLocationX.setBounds(97, 107, 48, 22);
					jTextFieldGridLocationX.setToolTipText("X");
				}
				{
					jTextFieldGridLocationY = new JTextField();
					jPanelAddAirport.add(jTextFieldGridLocationY);
					jTextFieldGridLocationY.setBounds(157, 107, 51, 22);
					jTextFieldGridLocationY.setToolTipText("Y");
				}
				{
					jButtonAddNewAirport = new JButton();
					jPanelAddAirport.add(jButtonAddNewAirport);
					jButtonAddNewAirport.setText("Add New Airport");
					jButtonAddNewAirport.setBounds(70, 156, 111, 22);
					jButtonAddNewAirport.addActionListener(new jButtonAddNewAirportHandle());
				}
			}
			{
				jLabelNewAirportInformation = new JLabel();
				frame.getContentPane().add(jLabelNewAirportInformation);
				jLabelNewAirportInformation.setText("New Airport Information");
				jLabelNewAirportInformation.setBounds(518, 398, 209, 21);
				jLabelNewAirportInformation.setFont(new java.awt.Font("Dialog",0,16));
				jLabelNewAirportInformation.setForeground(new java.awt.Color(102,102,204));
			}
			{
				jLabelNightMode = new JLabel();
				frame.getContentPane().add(jLabelNightMode);
				jLabelNightMode.setText("DayTime");
				jLabelNightMode.setBounds(930, 21, 66, 15);
				jLabelNightMode.setForeground(new java.awt.Color(204,102,0));
			}
			{
				jLabelFunction = new JLabel();
				frame.getContentPane().add(jLabelFunction);
				jLabelFunction.setText("Function List");
				jLabelFunction.setBounds(179, 401, 142, 18);
				jLabelFunction.setFont(new java.awt.Font("Dialog",0,16));
				jLabelFunction.setForeground(new java.awt.Color(102,102,204));
			}
			{
				jPanelFunctionList = new JPanel();
				frame.getContentPane().add(jPanelFunctionList);
				jPanelFunctionList.setBounds(7, 425, 500, 196);
				jPanelFunctionList.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				jPanelFunctionList.setLayout(null);
				{
					jButtonLoadTxtFile = new JButton();
					jPanelFunctionList.add(jButtonLoadTxtFile);
					jButtonLoadTxtFile.setText("Load Simulation Data");
					jButtonLoadTxtFile.setBounds(51, 159, 143, 22);
					jButtonLoadTxtFile.setActionCommand("Load txt data");
					//add listener
					jButtonLoadTxtFile.addActionListener(new jButtonLoadTextFileHandle());
				}
				{
					jButtonLoadReplayFile = new JButton();
					jPanelFunctionList.add(jButtonLoadReplayFile);
					jButtonLoadReplayFile.setText("Load ReplayFile");
					jButtonLoadReplayFile.setBounds(314, 159, 143, 22);
					jButtonLoadReplayFile.addActionListener(new jButtonLoadReplayFileHandle());
				}
				{
					jButtonShowPlotCode = new JButton();
					jPanelFunctionList.add(jButtonShowPlotCode);
					jButtonShowPlotCode.setText("Show Code/Name");
					jButtonShowPlotCode.setBounds(51, 111, 143, 22);
					jButtonShowPlotCode.addActionListener((e) ->{
						boolean b = false;
						if(jButtonShowPlotCode.getText().equals("Show Code/Name")) {
							jButtonShowPlotCode.setText("Hide Code/Name");
							b = false;
						}
						else {
							jButtonShowPlotCode.setText("Show Code/Name");
							b = true;
						}
						for(Entry<String, PlaneComponent> entry: planeImageMap.entrySet()) {
								entry.getValue().setDetailVisiable(b);				
						}
						for(Entry<String, AirportComponent> entry: airportImageMap.entrySet()) {
							entry.getValue().setDetailVisiable(b);
						}
					});
				}
				{
					jButtonRecord = new JButton();
					jPanelFunctionList.add(jButtonRecord);
					jButtonRecord.setText("Record/On");
					jButtonRecord.setBounds(51, 65, 143, 22);
					
					jButtonRecord.addActionListener((e) -> {
						if(jButtonRecord.getText().equals("Record/On"))  { //press to start record tickevent
							if(atmSimulation!=null && atmSimulation.getSimThreadStatus() == true) {
								atmSimulation.setRecordStatus(1);  //set Record value = 1, start record event
								jButtonRecord.setText("Record/Off");
							}else {
								JOptionPane.showMessageDialog(frame, "Please run Simulation first !");
							}
							
						}else if(jButtonRecord.getText().equals("Record/Off")) { //press to stop record
							atmSimulation.setWriteRecordFileSignal(1);  //give write replay file signal
							System.out.println("write successful!");
							jButtonRecord.setText("Record/On");
						}
					});
				}
				{
					jButtonPause = new JButton();
					jPanelFunctionList.add(jButtonPause);
					jButtonPause.setText("Pause");
					jButtonPause.setBounds(314, 22, 135, 22);
					jButtonPause.setSize(143, 22);
					jButtonPause.addActionListener((e) ->{
						atmSimulation.pause();
					});
				}
				{
					jButtonStart = new JButton();
					jPanelFunctionList.add(jButtonStart);
					jButtonStart.setText("Play");
					jButtonStart.setBounds(51, 22, 143, 22);
					jButtonStart.addActionListener((e) ->{
						//first check whether the data has loaded
						if(this.loadDataFlag == 0) {
							JOptionPane.showMessageDialog(frame, "Please load Simulation data first");
						}else if(atmSimulation.getSimThreadStatus() == false){
							atmSimulation.start();  //start Simulation
						}
					});
				}
				{
					jButtonReplay = new JButton();
					jPanelFunctionList.add(jButtonReplay);
					jButtonReplay.setText("Replay");
					jButtonReplay.setBounds(314, 65, 143, 22);
					jButtonReplay.addActionListener((e) -> {
						if(this.loadDataFlag == 0) {
							JOptionPane.showMessageDialog(frame, "Please load replay data first");
						}else if(atmSimulation.getSimThreadStatus() == false) {
							atmSimulation.setMode(1);  //set atmSimulation to replay mode
							atmSimulation.start();
						}
					});
				}
				{
					jButtonLoadJsonData = new JButton();
					jPanelFunctionList.add(jButtonLoadJsonData);
					jButtonLoadJsonData.setText("Load json data");
					jButtonLoadJsonData.setBounds(314, 111, 143, 22);
					jButtonLoadJsonData.setActionCommand("Load json data");
					jButtonLoadJsonData.addActionListener(new jButtonLoadTextFileHandle());
				}
				
			}
			{
				jPanelTest = new JPanel();
				frame.getContentPane().add(jPanelTest);
				jPanelTest.setBounds(187, 89, 10, 10);
			}

			{
				jLabelSimulationMode = new JLabel();
				frame.getContentPane().add(jLabelSimulationMode);
				jLabelSimulationMode.setText("NormalMode");
				jLabelSimulationMode.setBounds(529, 21, 103, 15);
				jLabelSimulationMode.setForeground(new java.awt.Color(0,153,0));
			}

			//init atmSimulation, register atmSimulation Listener
			atmSimulation = new ATMSimulation(new GridLocation(500,382));
			atmSimulation.addSimulationListener(this);
//			pack();
			frame.setSize(1200, 690);
			frame.setResizable(false);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private class jButtonLoadTextFileHandle implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(atmSimulation ==null) {
				atmSimulation = new ATMSimulation(new GridLocation(500, 382));
			}else if(loadDataFlag == 1 && jLabelSimulationMode.getText().equals("NormalMode")) {
				JOptionPane.showMessageDialog(frame, "Alread Loaded the Simulation data");
				return ;
			}else if(loadDataFlag == 1 && jLabelSimulationMode.getText().equals("ReplayMode")) {
				if(atmSimulation == null)
					atmSimulation = new ATMSimulation(new GridLocation(500,382));
				jLabelSimulationMode.setText("NormalMode");
				jLabelSimulationMode.setForeground(new Color(0, 153, 0));
			}
			// load data from airport.txt and traffic.txt file.
			ISimulationLoader loader;
			if(e.getActionCommand().equals("Load txt data")) {
				try {
					loader = new SimulationTextLoader(SimulationUtil.generalteInputStream("airports.txt"), SimulationUtil.generalteInputStream("traffic.txt"));
					((SimulationTextLoader)loader).initializeInputStream();
					atmSimulation.loadSimulation(loader);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(e.getActionCommand().equals("Load json data")) {
				try {
					loader = new SimulationJSONLoader(SimulationUtil.generalteInputStream("simulationData.json"));
					((SimulationJSONLoader)loader).initializeJsonData();
					atmSimulation.loadSimulation(loader);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			if(airportVector == null) {
				airportVector = new Vector<String>();
			}
			airportVector.clear();
			for(int i = 0 ; i< atmSimulation.getAirports().size() ; i++) {
				airportVector.add(((Airport)(atmSimulation.getAirports().get(i))).toString());
			}
			jListAirports.setListData(airportVector);
			
			atmSimulation.setMode(0);  //set normal mode
			
			//draw airport and plane image on map
			if(planeImageMap == null)
				planeImageMap = new HashMap<String, PlaneComponent>();
			if(airportImageMap == null)
				airportImageMap = new HashMap<String, AirportComponent>();
			drawPlane();	
			drawAirport();
			loadDataFlag = 1;
		}
	}
	public void drawAirport() {
		List<IAirport> airportList = new ArrayList<IAirport>();
		if(jLabelSimulationMode.getText().equals("ReplayMode")) { //replay mode, data is in atmSimulation.tickeventreplay,decided by tick
			airportList = atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getAirportList();
		}else if(jLabelSimulationMode.getText().equals("NormalMode"))
			airportList = atmSimulation.getAirports();   //Normal mode, data is in atmSimulation.getAirports()
		if(loadDataFlag == 0) {
			for(IAirport airport: airportList) {
				createAirportLabel(airport.getCode(),airport.getLocation().getX(),airport.getLocation().getY());
			}
		}else if(loadDataFlag == 1 && jLabelSimulationMode.getText().equals("ReplayMode")) {
			// if Simulation is on ReplayMode, airport could be added. just check TickEventReplayFile.getAirportCreateList()
			if(atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getAirportCreateList()!=null) {
				List<IAirport> airportCreateList = atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getAirportCreateList();
				for(int i = 0 ; i < airportCreateList.size(); i++) {
					Airport airport = (Airport)airportCreateList.get(i);
					createAirportLabel(airport.getCode(),airport.getLocation().getX(),airport.getLocation().getY());
				}
			}
		}
	}
	
	public void createAirportLabel(String code, int x, int y) {
		AirportComponent airportLabel = new AirportComponent(code, "black_Airport.pic");
		airportLabel.addMouseListener(new jLabelAirportComponentListener());
		airportLabel.setBounds(x*5, y*5, AIRPORT_IMAGE_WIDTH, AIRPORT_IMAGE_HEIGHT);
		airportLabel.setName(code);
		airportLabel.addMouseListener(new jLabelAirportComponentListener());
		airportImageMap.put(code,airportLabel);
		frame.getLayeredPane().add(airportLabel,0);
	}
	
	public void createPlaneLabel(String code, int x, int y) {
		PlaneComponent pcom = new PlaneComponent(code, "gray_plane.jpg");
		pcom.setBounds(x*5, y*5, PLANE_IMAGE_WIDTH, PLANE_IMAGE_HEIGHT);
		pcom.setName(code); //set PlaneComponent name as plane callsign, easy to remove and find
		pcom.addMouseListener(new jLabelPlaneComponentListener());
		planeImageMap.put(code, pcom);
		frame.getLayeredPane().add(pcom,0);
	}
	
	
	public void drawPlane() {
		List<IPlane> planeList = new ArrayList<IPlane>();
		if(jLabelSimulationMode.getText().equals("ReplayMode")) { //replay mode, data is in atmSimulation.tickeventreplay,decided by tick
			
			planeList = atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getPlaneList();
			
			
		}else if(jLabelSimulationMode.getText().equals("NormalMode")) {
			planeList = atmSimulation.getPlanes();   //Normal mode, data is in atmSimulation.getPlanes()
		}
		if(loadDataFlag == 0) {  //first time to load data
			for(IPlane plane: planeList) {
				createPlaneLabel(plane.getName(), plane.getLocation().getX(), plane.getLocation().getY());
			}
		}else {  //just redraw data, if some plane's location has changed, than changed it's location
			for(IPlane plane: planeList) {
				int x = plane.getLocation().getX()*5;
				int y = plane.getLocation().getY()*5;
				if(x >= atmSimulation.getMapDimensions().getX()*5) {
					x = atmSimulation.getMapDimensions().getX();
				}	
				if(y >= atmSimulation.getMapDimensions().getY()*5) {
					y = atmSimulation.getMapDimensions().getY();
				}
				planeImageMap.get(plane.getName()).setBounds(x,y,PLANE_IMAGE_WIDTH,PLANE_IMAGE_HEIGHT);
			}
		}
	}
	
	private class jListAirportsHandle implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			String str = (String) jListAirports.getSelectedValue();
			String airportCode = null;
			if(str!=null) {
				airportCode = SimulationUtil.matchCode(str);
				if(atmSimulation.getMode() == 0) {  //normal mode
					for(int i = 0 ; i < atmSimulation.getTrafficList().size(); i ++) {
						Traffic traffic = atmSimulation.getTrafficList().get(i);
					jListArrivals.setListData(SimulationUtil.generateArrivals(atmSimulation.getTrafficList(), airportCode));
					jListDepartures.setListData(SimulationUtil.generateDepartures(atmSimulation.getTrafficList(), airportCode));
					}
				}
				else { //replay mode
					jListArrivals.setListData(SimulationUtil.generateArrivalsReplayMode(atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getPlaneList(), airportCode));
					jListDepartures.setListData(SimulationUtil.generateDeparturesReplayMode(atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getPlaneList(), airportCode));
				}
			}
			//change color for selected airport
			if(airportImageMap != null) {
				airportImageMap.get(airportCode).setImagePath("red_Airport.pic");
				for(Entry<String, AirportComponent> entry: airportImageMap.entrySet()) {
					if(!entry.getKey().equals(airportCode))
						entry.getValue().setImagePath("black_Airport.pic");
				}
			}
		}
	}
	
	private class jButtonAddNewAirportHandle implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			String name = jTextFieldNewAirportName.getText();
			String code = jTextFieldNewAirportCode.getText();
		    String x = jTextFieldGridLocationX.getText();
			String y = jTextFieldGridLocationY.getText();
			// check input information not null
			if(airportImageMap == null) {
				airportImageMap = new HashMap<String, AirportComponent>();
			}
			if(jLabelSimulationMode.getText().equals("ReplayMode")) {
				JOptionPane.showMessageDialog(frame, "ReplayMode can't add Airport!");
			}
			if(name.equals("") || code.equals("") || x.equals("") || y.equals("")) {
				JOptionPane.showMessageDialog(frame, "name or code or location is null, please check again!");
			}else if(airportImageMap.get(code) !=null) {
				JOptionPane.showMessageDialog(frame, "This airport already exists! Please check again");
			}else{
				GridLocation location = new GridLocation(Integer.parseInt(x),Integer.parseInt(y));
				atmSimulation.addAirport(code, name, location);
				createAirportLabel(code, location.getX(), location.getY());
				
				//load new data to AirportList
				if(airportVector == null) {
					airportVector = new Vector<String>();
				}
				airportVector.add(name+"(" + code + ") -" +location.toString());
				jListAirports.setListData(airportVector);
				//clear input data
				jTextFieldNewAirportName.setText("");
				jTextFieldNewAirportCode.setText("");
				jTextFieldGridLocationX.setText("");
				jTextFieldGridLocationY.setText("");
			}
		}
	}
	
	private class jButtonLoadReplayFileHandle implements ActionListener{
		//change mode label, simulation mode, label text , clear data, reload data from json file
		@Override
		public void actionPerformed(ActionEvent e) {
			if(atmSimulation == null) {
				atmSimulation = new ATMSimulation(new GridLocation(500,382));
			}else if(jLabelSimulationMode.getText().equals("NormalMode") && airportVector != null) {
				//clear normal mode data
				for(Entry<String, PlaneComponent> entry: planeImageMap.entrySet()) {
					entry.getValue().setVisible(false);
				}
				for(Entry<String, AirportComponent> entry : airportImageMap.entrySet()) {
					entry.getValue().setVisible(false);
				}
				//let thread stop
				atmSimulation.pause();
				airportVector.clear();
				planeImageMap.clear();
				airportImageMap.clear();
				jListAirports.setListData(airportVector);
				loadDataFlag = 0;  //as for replay mode, this is first time to load data
			}else if(jLabelSimulationMode.getText().equals("ReplayMode") && airportVector != null && loadDataFlag == 1) {
				//check whether replay data has been loaded
				JOptionPane.showMessageDialog(frame,"Data has already loaded!");
				return ;
			}
			//all condition has passed, start to load replay data
			//change jLabelMode text first
			jLabelSimulationMode.setText("ReplayMode");
			jLabelSimulationMode.setForeground(new Color(0, 153, 204));
			atmSimulation.setMode(1);
			atmSimulation.setSimTicks(0);
			try {
				atmSimulation.loadFlightPathsForReplay(SimulationUtil.generalteInputStream("replayData.json"));
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(frame, e1.toString());
				e1.printStackTrace();
			}
			
			if(airportVector == null) {
				airportVector = new Vector<String>();
			}
			if(planeImageMap == null)
				planeImageMap = new HashMap<String, PlaneComponent>();
			if(airportImageMap == null)
				airportImageMap = new HashMap<String, AirportComponent>();
			//load data to jListAirports (only load first relay tick data is enough) 
			List<IAirport> airport = atmSimulation.getTickEventReplayFile().get(atmSimulation.getSimTime()).getAirportList();
			
			for(int i = 0 ; i< airport.size() ; i++) {
				airportVector.add(((Airport)airport.get(i)).toString());
			}
			jListAirports.setListData(airportVector);
			
			drawPlane();
			drawAirport();
			loadDataFlag = 1;  //change load data flag
		}
	}
	private class jLabelPlaneComponentListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			PlaneComponent com = (PlaneComponent)e.getComponent();
			if(com.getImagePath().equals("red_plane.jpg")) {
				com.setImagePath("gray_plane.jpg");
			}else {
				com.setImagePath("red_plane.jpg");
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
	
	private class jLabelAirportComponentListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			AirportComponent com = (AirportComponent)e.getComponent();
			if(com.getImagePath().equals("black_Airport.pic")) {
				com.setImagePath("red_Airport.pic");
			}else {
				com.setImagePath("black_Airport.pic");
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}	
	}

	public JFrame getFrame() {
		return frame;
	}
	
	
	@Override
	public void notifySimHasChanged(SimulationEvent event) {
		ATMSimulation changeEvent = (ATMSimulation)event.getSource();
		if(atmSimulation == null)
			atmSimulation = changeEvent;
//		this.atmSimulation = event.getSource();
		//更新飞机位置信息
		
		//check是否增加了新飞机
		for(int i = 0 ; i < atmSimulation.getPlaneCreateList().size() ; i ++) {
			Plane plane = (Plane)atmSimulation.getPlaneCreateList().get(i);
			if(planeImageMap.get(plane.getName()) == null) {  //this plane hasn't been created on the map
				createPlaneLabel(plane.getName(), plane.getLocation().getX(), plane.getLocation().getY());
			}
		}
		
		//飞机着陆
		for(int i = 0 ; i < atmSimulation.getPlanes().size(); i++) {
			Plane plane = (Plane) atmSimulation.getPlanes().get(i);
			if(plane.getStatus() == 4){  // already landed
				planeImageMap.get(plane.getName()).setVisible(false);
				planeImageMap.remove(plane.getName());
				atmSimulation.getPlanes().remove(i);
			}
		}
		//check是否有意外发生
		drawPlane();
		if(atmSimulation.getMode() == 1)  { //replay mode ,need to draw airport
			drawAirport();
		}
	}

}
