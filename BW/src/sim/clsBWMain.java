/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim;

import org.jfree.data.xy.XYSeries;

import ec.util.MersenneTwisterFast;

import sim.creation.clsLoader;
import sim.creation.simpleLoader.clsSimpleLoader;
import sim.engine.Schedule;
import sim.engine.SimState;
import statictools.clsGetARSPath;


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
	
	public static String[] configurationParamter;

     
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
	@Override
	public void start()
	{
		super.start();
		//creating and registering objects...
		clsLoader oLoader = null;
		String[] debugDummy = clsBWMain.configurationParamter;

//		if (clsBWMain.configurationParamter[0].equals("0")) 
		{
		
//		oLoader = new clsLifeCycleLoader(this, "", 200, 200, 5, 3, 1, 2, eLifeCycleDUs.IfThenElse, eLifeCycleDUs.IfThenElse);
//		oLoader = new clsSimpleLoader(this, "", 200, 200, 1, 2, 5, 3, 1, 1, 1, 1);

//		oLoader = new clsLifeCycleLoader(this, "", 200, 200, 5, 3, 1, 2, eLifeCycleDUs.IfThenElse, eLifeCycleDUs.IfThenElse);
//		oLoader = new clsSimpleLoader(this, "", 200, 200, 1, 2, 5, 3, 1, 1, 1, 1);
			oLoader = new clsSimpleLoader(this, "", 200, 200, 1, 0, 0, 0, 0, 0, 10, 1);
//		} else {
//			oLoader = new clsSimpleLoader(this, "", 100, 100, 1, 0, 0, 0, 0, 0, 10, 1);
		}
//		oLoader = new clsSimpleXMLLoader(this, "", bw.sim.clsBWMain.msArsPath + "/src/xml/xmlSimpleXMLLoader/config1.xml");
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
		configurationParamter = args;
		
		setArsPath();
		doLoop(clsBWMain.class, args);
		System.exit(0);
	}
	
	public static void setArsPath()
	{
		msArsPath = clsGetARSPath.getArsPath()+"/BW";
	}

}// end class
