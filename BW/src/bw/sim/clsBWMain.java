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

import bw.factories.clsSingletonMasonGetter;
import bw.sim.bwgenerator.clsAgentLoader;
import bw.sim.bwgenerator.clsEntityLoader;
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
	
	
	/** activates/shows the charting panel
	 * TODO clemens: deactivated for now, has to set by config.xml later! */
	public boolean mbChartDisplay = false; 
	public JFreeChart  moTestChart;
	public XYSeries moTestSeries = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wofür das genau ist!
	public XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moTestSeries); //TODO clemens

	/**
	 * Continuous2D is a Field: a representation of space. In particular, Continuous2D 
	 * represents continuous 2-dimensional space it is actually infinite: the width 
	 * and height are just for GUI guidelines (starting size of the window). */
    public Continuous2D moGameGridField;

    //dimensions of the playground
    public double mnXMin = 0;
    public double mnXMax = 100;
    public double mnYMin = 0;
    public double mnYMax = 100;
    
    /**activates/shows the charting panel, default is false
	 * @return the mbChartDisplay */
	public boolean getmbChartDisplay() {
		return mbChartDisplay;
	}
    

	public clsBWMain(long pnSeed){
		this(pnSeed, 200, 200);
	}
	

    public clsBWMain(long pnSeed, int pnWidth, int pnHeight) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
	    mnXMax = pnWidth; 
	    mnYMax = pnHeight;
	    //createGrids(); CHKME warum wolltest du das hier RooL? ist in der start() Methode eh gut aufgehoben!
    }
	
    
    void createGrids()
    {       
    	moGameGridField = new Continuous2D(25, (mnXMax - mnXMin), (mnYMax - mnYMin));
    }

    /**
	 * start is the method called when the simulation starts but before any agents have been pulsed.
	 */
	public void start()
	{
		super.start();
		
		createGrids();
		
		clsSingletonMasonGetter.setPhysicsEngine2D(new PhysicsEngine2D());
		
		PhysicsEngine2D objPE = clsSingletonMasonGetter.getPhysicsEngine2D();
		
		//creating and registring objects...
		loadObjects(objPE);
		
		//clear the charts
		moTestSeries.clear(); //TODO Clemens for charting
		//TODO clemens: add charts/statistics to schedule here
		
		//schedules a steppable to be repeatedly stepped, forever, once per unit timestep
		schedule.scheduleRepeating(objPE);
	}

	/**
	 * Method for loading all objects into the mason world.
	 * could be extended to a xml loading?
	 *
	 * @param objPE
	 */
	private void loadObjects(PhysicsEngine2D objPE) {
		//add walls
		clsWorldBoundaries.loadWorldBoundaries(moGameGridField, objPE, this);
		//add inimate objects
		clsEntityLoader.loadInanimate(moGameGridField, objPE, this);
		//add animate
		clsEntityLoader.loadAnimate(moGameGridField, objPE, this);
		
		clsAgentLoader.loadAgents(moGameGridField, objPE, this, mnXMin, mnXMax, mnYMin, mnYMax);
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
