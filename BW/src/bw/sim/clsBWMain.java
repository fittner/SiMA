/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim;

import javax.swing.JOptionPane;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import sim.display.SimApplet;
import sim.engine.*;
import sim.field.continuous.*;

/**
 * Main function for simulation
 * @author muchitsch
 * 
 */
public class clsBWMain extends SimState{

	/**
	 * activates/shows the charting panel
	 */
	public boolean mbChartDisplay = false; //TODO clemens: deactivated for now, has to set by config.xml later! 
	public JFreeChart  moTestChart;
	public XYSeries moAgents_series = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wofür das genau ist!
	public XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moAgents_series); //TODO clemens

    public Continuous2D moFieldEnvironment;
	
	public clsBWMain(long prSeed){
		super(prSeed);
	}

	
	public void start()
	{
		super.start();
	}
	
	/**
	 * The one and only Main
	 *
	 * @param poArgs
	 */
	public static void main(String[] poArgs)
	{
		doLoop(clsBWMain.class, poArgs);
		System.exit(0);
	}

	
	


	
}// end class
