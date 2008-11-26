/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim;

import sim.engine.*;

/**
 * Main function for simulation
 * @author muchitsch
 * 
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
