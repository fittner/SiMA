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

/**
 * Main function for simulation
 * @author muchitsch
 * 
 */
public class clsMain extends SimState{

	public boolean mbChart_display = false;
	public JFreeChart  moTestChart;
	public XYSeries agents_series = new XYSeries("Agents");
	public XYSeriesCollection agents_series_coll = new XYSeriesCollection(agents_series);

	public clsMain(long seed){
		super(seed);
	}

	
	public void start()
	{
		super.start();
	}
	
	/**
	 * The one and only Main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		doLoop(clsMain.class, args);
		System.exit(0);
	}

	
	


	
}// end class
