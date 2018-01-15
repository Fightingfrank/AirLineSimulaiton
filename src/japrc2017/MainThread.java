package japrc2017;

/*
 * ExamNumber : Y3851551
 */

import javax.swing.SwingUtilities;

import japrc2017.UI.UserInterface;

public class MainThread {
	
	private UserInterface inst;
	private ATMSimulation atmSimulation;
	
	public MainThread() {
		inst = new UserInterface();
		atmSimulation = new ATMSimulation(new GridLocation(500, 382));
		atmSimulation.addSimulationListener(inst);
		
	}
	
	public static void main(String[] args) {
		
		MainThread mainThread = new MainThread();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainThread.inst.getFrame().setLocationRelativeTo(null);
				mainThread.inst.getFrame().setVisible(true);
			}
		});
		
		
	}
}
