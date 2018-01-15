package japrc2017;

/*
 * ExamNumber : Y3851551
 */

public class Airport implements IAirport{
	
	private String code;
	private String name;
	private GridLocation location;
	private double takeOffProbability;
	private int planeNumber;
	private boolean status; //midnight or daytime
	
	public Airport(String code) {
		this.code = code;
		this.planeNumber = 1;
		this.takeOffProbability = 0;
		this.status = false;
	}
	
	public Airport(String code, String name, GridLocation location) {
		this.code = code;
		this.name = name;
		this.location = location;
		this.planeNumber = 1;
		this.takeOffProbability = 0;
		this.status = false;
	}
	
	public Airport(String code, String name, GridLocation location, double takeOffProbability) {
		this.code = code;
		this.name = name;
		this.location = location;
		this.planeNumber = 1;
		this.takeOffProbability = takeOffProbability;
		this.status = false;
	}
	
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public int getPlaneNumber() {
		return planeNumber;
	}
	public void setPlaneNumber(int number) {
		this.planeNumber = number;
	}

	@Override
	public GridLocation getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public boolean getOpen() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTakeOffProbability(double prob) {
		// TODO Auto-generated method stub
		this.takeOffProbability = prob;
	}

	@Override
	public double getTakeOffProbability() {
		// TODO Auto-generated method stub
		return takeOffProbability;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + this.code + ") -" + this.location.getX() + "," + this.location.getY();
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	

	public boolean getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

}
