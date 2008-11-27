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
	public XYSeries moTestSeries = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wofür das genau ist!
	public XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moTestSeries); //TODO clemens

	/**
	 * Continuous2D is a Field: a representation of space. In particular, Continuous2D 
	 * represents continuous 2-dimensional space it is actually infinite: the width 
	 * and height are just for GUI guidelines (starting size of the window). 
	 */
    public Continuous2D moGameGridField;

    //dimensions of the playground
    public double xMin = 0;
    public double xMax = 100;
    public double yMin = 0;
    public double yMax = 100;
    
    
	public clsBWMain(long seed){
		this(seed, 200, 200);
	}
	
    public clsBWMain(long seed, int width, int height) {
    	super(new MersenneTwisterFast(seed), new Schedule());
	    xMax = width; 
	    yMax = height;
	    //createGrids(); CHKME warum wolltest du das hier RooL? ist in der start() Methode eh gut aufgehoben!
    }
	
    
    void createGrids()
    {       
    	moGameGridField = new Continuous2D(25, (xMax - xMin), (yMax - yMin));
    }

    /**
	 * start is the method called when the simulation starts but before any agents have been pulsed.
	 */
	public void start()
	{
		super.start();
		
		createGrids();
		
		PhysicsEngine2D objPE = new PhysicsEngine2D();
		
		//creating and registring objects...
		
		//add world and agents
		clsWorldBoundaries.loadWorldBoundaries(moGameGridField, objPE);
		clsAgentLoader.loadAgents(moGameGridField, objPE, this, xMin, xMax, yMin, yMax);
		
		//clear the charts
		moTestSeries.clear(); //TODO Clemens for charting
		
		//TODO clemens: add charts/statistics to schedule here
		
		//schedules a steppable to be repeatedly stepped, forever, once per unit timestep
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
