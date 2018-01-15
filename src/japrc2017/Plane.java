package japrc2017;

/*
 * ExamNumber : Y3851551
 */

import japrc2017.util.SimulationUtil;

public class Plane implements IPlane{
	
	private String name;
	private GridLocation location;
	private Airport originAirport;
	private Airport destinationAirport;
	//status: 1. plane create ; 2 already take off;3 arrive destination;4 land
	private int status;
	private double speed;
	private double realX,realY;   //replace the real double location due to plane movement
	private int flyToDestinationTickCost;
	private double pathLength; //replace how long that this plane already flying, it use speed*tick
	public Plane(String callsign) {
		this.name = callsign;
		this.status = 2;
		this.speed = 1;
		this.flyToDestinationTickCost = 0;
		this.pathLength = this.speed;
	}
	
	public Plane(String callsign, IAirport originAirport, IAirport destinationAirport) {
		this.name = callsign;
		this.originAirport = (Airport)originAirport;
		this.destinationAirport = (Airport)destinationAirport;
		this.location = originAirport.getLocation();
		this.speed = 1;
		this.status = 2;
		this.flyToDestinationTickCost = 0;
		this.realX = originAirport.getLocation().getX();
		this.realY = originAirport.getLocation().getY();
		this.flyToDestinationTickCost = SimulationUtil.calculateFlyTicksCost(this);
		this.pathLength = this.speed;
	}

	public Plane(String name, GridLocation location, IAirport originAirport, IAirport destinationAirport) {
		this.name = name;
		this.location = location;
		this.originAirport = (Airport)originAirport;
		this.destinationAirport = (Airport)destinationAirport;
		this.speed = 1;
		this.status = 2;
		this.flyToDestinationTickCost = 0;
		this.realX = location.getX();
		this.realY = location.getY();
		this.flyToDestinationTickCost = SimulationUtil.calculateFlyTicksCost(this);
		this.pathLength = this.speed;
	}
	
	public Plane(String name, GridLocation location, String originAirport, String destinationAirport) {
		this.name = name;
		this.location = location;
		this.originAirport = new Airport(originAirport);
		this.destinationAirport = new Airport(destinationAirport);
		this.status = 2;
		this.speed = 1;
		this.flyToDestinationTickCost = 0;
		this.realX = location.getX();
		this.realY = location.getY();
		this.pathLength = this.speed;
	}
	
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public int getFlyToDestinationTickCost() {
		return flyToDestinationTickCost;
	}
	
	public void setFlyToDestinationTickCost(int flyToDestinationTickCost) {
		this.flyToDestinationTickCost = flyToDestinationTickCost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public GridLocation getLocation() {
		// TODO Auto-generated method stub
		return location;
	}
	
	public void setLocation(GridLocation location) {
		this.location = location;
	}
	
	@Override
	public IAirport getOrigin() {
		// TODO Auto-generated method stub
		return originAirport;
	}

	@Override
	public IAirport getDestination() {
		// TODO Auto-generated method stub
		return destinationAirport;
	}

	@Override
	public void setSpeed(double multiplier) {
		this.speed = multiplier;
	}
	
	public double getSpeed() {
		return this.speed;
	}

	@Override
	public void tick() {
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.name == ((Plane)obj).getName();
	}
	
	public double getRealX() {
		return this.realX;
	}
	
	public void setRealX(double x) {
		this.realX = x;
	}
	public double getRealY() {
		return this.realY;
	}
	public void setRealY(double y) {
		this.realY = y;
	}
	
	public double getPathLength() {
		return this.pathLength;
	}
	public void setPathLength(double length) {
		this.pathLength = length;
	}
}
