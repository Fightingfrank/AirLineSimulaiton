package japrc2017;

public final class SimulationException extends RuntimeException
{
	
	private String msg;

    public SimulationException(String message){
        this.msg = message;
    }
    
    @Override
    public String toString(){
        return msg;
    }
}
