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

import bw.sim.bwgenerator.clsAgentLoader;
import bw.sim.bwgenerator.clsWorldBoundaries;

import ec.util.*;

import sim.physics2D.*;
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
	
	public clsBWMain(long seed){
		this(seed, 200, 200);
	}
	
    public clsBWMain(long seed, int width, int height) {
    	super(new MersenneTwisterFast(seed), new Schedule());
	    xMax = width; 
	    yMax = height;
	    createGrids();
    }
	
	//dimensions of the playground
    public double xMin = 0;
    public double xMax = 100;
    public double yMin = 0;
    public double yMax = 100;
    void createGrids()
    {       
    	moFieldEnvironment = new Continuous2D(25, (xMax - xMin), (yMax - yMin));
    }

	
	public void start()
	{
		super.start();
		createGrids();
		
		PhysicsEngine2D objPE = new PhysicsEngine2D();
		
		//creating and registring objects
		
		clsWorldBoundaries.loadWorldBoundaries(moFieldEnvironment, objPE);
		clsAgentLoader.loadAgents(moFieldEnvironment, objPE, this, xMin, xMax, yMin, yMax);
		
		schedule.scheduleRepeating(objPE);
	}
	
	/**
	 * The one and only Main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		doLoop(clsBWMain.class, args);
		System.exit(0);
	}

}// end class
