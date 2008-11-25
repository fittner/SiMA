package bw.sim;

import sim.engine.*;


/**
 * @author muchitsch
 * Main function for simulation
 */
public class clsMain extends SimState{

	public clsMain(long seed){
		super(seed);
	}

	public void start()
	{
		super.start();
	}
	
	public static void main(String[] args)
	{
		doLoop(clsMain.class, args);
		System.exit(0);
	}
	
}// end class
