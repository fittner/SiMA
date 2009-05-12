/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim;

import org.jfree.data.xy.XYSeries;

import ec.util.MersenneTwisterFast;

import sim.engine.Schedule;
import sim.engine.SimState;

import bw.sim.creation.clsLoader;
import bw.sim.creation.lifeCycle.clsLifeCycleLoader;
import bw.sim.creation.simpleLoader.clsSimpleLoader;
import bw.sim.creation.simpleXMLLoader.clsSimpleXMLLoader;

/**
 * Main function for simulation
 * @author muchitsch
 * 
 */
public class clsBWMain extends SimState{
	
	public static String msArsPath;
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 24.02.2009, 14:45:41
	 */
	private static final long serialVersionUID = 1L;
	/** activates/shows the charting panel
	 * TODO clemens: deactivated for now, has to set by config.xml later! */
	private boolean mbChartDisplay = false; 
//	private JFreeChart  moTestChart;
	private XYSeries moTestSeries = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wof�r das genau ist!
//	private XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moTestSeries); //TODO clemens

     
    public clsBWMain(long pnSeed) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
	    //createGrids(); CHKME warum wolltest du das hier RooL? ist in der start() Methode eh gut aufgehoben!
    }
	

    /**activates/shows the charting panel, default is false
	 * @return the mbChartDisplay */
	public boolean getmbChartDisplay() {
		return mbChartDisplay;
	}
    
	
    /**
	 * @author deutsch
	 * 25.02.2009, 15:03:11
	 * 
	 * @return the moTestSeries
	 */
	public XYSeries getMoTestSeries() {
		return moTestSeries;
	}


	/**
	 * start is the method called when the simulation starts but before any agents have been pulsed.
	 */
	public void start()
	{
		super.start();
		//creating and registering objects...
		clsLoader oLoader = null;
		
		oLoader = new clsLifeCycleLoader(this, 200, 200, 5, 3, 1, 2);
//		oLoader = new clsSimpleLoader(this, 200, 200, 1, 2, 5, 3, 1);
//		oLoader = new clsSimpleLoader(this, 200, 200, 1, 0, 0, 0, 1);
//		oLoader = new clsSimpleXMLLoader(this, bw.sim.clsBWMain.msArsPath + "/src/xml/xmlSimpleXMLLoader/config1.xml");
		oLoader.loadObjects();
		
		//clear the charts
		moTestSeries.clear(); //TODO Clemens for charting
		//TODO clemens: add charts/statistics to schedule here
	}

	/**
	 * The one and only Main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		setArsPath();
		doLoop(clsBWMain.class, args);
		System.exit(0);
	}
	
	public static void setArsPath()
	{
		if (System.getProperty("file.separator").equals("/"))
			msArsPath = System.getProperty("user.home") + "/ARS/PA/BWv1/BW"; // Unix
		else if (System.getProperty("file.separator").equals("\\"))
			msArsPath = "S:/ARS/PA/BWv1/BW"; // Windows
		else throw new NullPointerException("Spooky OS detected, can't find ARS-Root-Dir.");
	}

}// end class
