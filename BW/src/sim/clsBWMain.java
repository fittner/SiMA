/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim;

import javax.swing.JDialog;

import org.jfree.data.xy.XYSeries;

import config.clsBWProperties;
import ec.util.MersenneTwisterFast;
import sim.creation.clsLoader;
import sim.creation.simplePropertyLoader.clsSimplePropertyLoader;
import sim.engine.Schedule;
import sim.engine.SimState;
import statictools.clsGetARSPath;


/**
 * Main function for simulation
 * @author muchitsch
 * 
 */
public class clsBWMain extends SimState{
	/**
	 * DOCUMENT (tobias) - insert description 
	 * 
	 * @author tobias
	 * Jul 26, 2009, 4:48:51 PM
	 */
	private static final long serialVersionUID = -1952879483933572186L;
	
	/** activates/shows the charting panel
	 * TODO clemens: deactivated for now, has to set by config.xml later! */
	//commented by TD
	private boolean mbChartDisplay = false;
	private XYSeries moTestSeries = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wof�r das genau ist!
	
//	private JFreeChart  moTestChart;
//	private XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moTestSeries); //TODO clemens
	
	private String[] moArgs;
     
    public clsBWMain(long pnSeed, String[] args) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
    	moArgs = args;
    }
    
    public clsBWMain(long pnSeed) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
    	moArgs = new String[]{};
    }    
	
	/**
	 * start is the method called when the simulation starts but before any agents have been pulsed.
	 */
	@Override
	public void start()
	{
		super.start();
		
		//IMPORTANT: do not change anything here just to run the configuration
		//you like! you can change the command line arguments easily:
		//  1. right click on clsBWMainWithUI.java in the package explorer.
		//  2. select run as or debug as (they have different configs)
		//  3. select run or debug config
		//  4. select the second tab "arguments"
		//  5. enter either the selected index or the filename into the textbox program arguments
		//  6. apply
		//  7. start debuggin / running
		//
		// the only thing which is allowed is to add another file in the switch statement.
		
		//creating and registering objects...
		String oFilename = "";		
		try {
			int i = Integer.valueOf( moArgs[0] );
			switch(i) {
				case 0: oFilename = "testsetup.main.properties"; break;
				case 1: oFilename = "funguseater.main.properties"; break;
				case 2: oFilename = "hare_vs_lynx.main.properties"; break;
				case 3: oFilename = "one_bubble.properties"; break;
				default: oFilename = "testsetup.main.properties"; break;
			}
		} catch (NumberFormatException e) {
			oFilename = moArgs[0]; // given parameter is not an index number. it is probably a filename
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			oFilename = "testsetup.main.properties"; // no parameters given - used default config
		}
		
		String oPath = clsGetARSPath.getConfigPath();
		try {
			oPath = moArgs[1];
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			//do nothing 
		}
	
		clsBWProperties oProp = clsBWProperties.readProperties(oPath, oFilename);
		
		clsBWFastEntityAdapter oAdapterFrame = new clsBWFastEntityAdapter(null, "BWv1 - Fast Entity Adapter", oProp);
		oAdapterFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		oAdapterFrame.setModal(true);
		oAdapterFrame.setVisible(true);
		
		clsLoader oLoader = new clsSimplePropertyLoader(this, oProp);
		oLoader.loadObjects();
		
		//clear the charts
		moTestSeries.clear(); //TODO Clemens for charting
		//TODO clemens: add charts/statistics to schedule here 
	}


	/**
	 * main - starts the simulation without gui
	 *
	 * TODO add arguments similarily to clsBWMainWithUI. caution! doLoop has its own arguments ...
	 *
	 * @author tobias
	 * Jul 26, 2009, 5:12:07 PM
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		doLoop(clsBWMain.class, args);
		System.exit(0);
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
}
