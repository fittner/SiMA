/**
 * 
 */
package sim;

//import javax.swing.JDialog;
import org.jfree.data.xy.XYSeries;
import config.clsBWProperties;
import du.enums.eDecisionType;
import ec.util.MersenneTwisterFast;
import sim.creation.clsLoader;
import sim.creation.simplePropertyLoader.clsSimplePropertyLoader;
import sim.engine.Schedule;
import sim.engine.SimState;
import statictools.clsGetARSPath;
import statictools.clsSimState;


/**
 * Main function for simulation
 * 
 * Params for main(arcs[])
 *  - -config
 *  - -impstages
 *  - -adapter
 *  - -path test test test
 * blabla
 * @author muchitsch
 * 
 */

/*HZ - added some documentation for git test*/
public class clsBWMain extends SimState{
	/**
	 * @author tobias
	 * Jul 26, 2009, 4:48:51 PM
	 */
	private static final long serialVersionUID = -1952879483933572186L;
	
	/** activates/shows the charting panel
	 * TODO clemens: deactivated for now, has to set by config.xml later! */
	//commented by TD
	@Deprecated
	private boolean mbChartDisplay = false;

	@Deprecated
	private XYSeries moTestSeries = new XYSeries("Agents"); //TODO clemens name passt nicht, muss erst schauen wof�r das genau ist!
	
//	private JFreeChart  moTestChart;
//	private XYSeriesCollection moAgents_series_coll = new XYSeriesCollection(moTestSeries); //TODO clemens
	
	private String[] moArgs;
     
    public clsBWMain(long pnSeed, String[] args) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
    	moArgs = args;
    	clsSimState.setSimState(this);
    }
    
    public clsBWMain(long pnSeed) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
    	moArgs = new String[]{};
    	clsSimState.setSimState(this);
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
		
		String oFilename = argumentForKey("-config", moArgs, 0);
		
		if (oFilename != null) {
			try {
				int i = Integer.valueOf( oFilename );
				switch(i) {
					case 0: oFilename = "testsetup.main.properties"; break;
					case 1: oFilename = "funguseater.main.properties"; break;
					case 2: oFilename = "hare_vs_lynx.main.properties"; break;
					case 3: oFilename = "one_bubble.properties"; break;
					case 4: oFilename = "surfer_andi.own.dont.touch.this.world.main.properties"; break;
					case 5: oFilename = "energysource_and_arsin_HZ.main.properties"; break;
					case 6: oFilename = "clemens.own.dont.touch.this.world.main.properties"; break;
					case 7: oFilename = "aw.own.dont.touch.this.world.main.properties"; break;
					case 8: oFilename = "one_bubble_one_cake_one_staticbubble.properties"; break;
					case 9: oFilename = "TD_PhD.main.properties"; break;
					default: oFilename = "testsetup.main.properties"; break;
				}
			} catch (NumberFormatException e) {
				// filename given - no further action necessary
			}
		} else {
			oFilename = "testsetup.main.properties"; // no parameters given - used default config			
		}
		
		// config file for selection of implementationstages of the various modules in PA decision unit
		
		String oImplementationStagesFile = argumentForKey("-impstages", moArgs, 0);
		
		// show fast config adapter
		
		String oAdapter = argumentForKey("-adapter", moArgs, 0);
		Boolean nAdapter = new Boolean(oAdapter);
		
		//different path
		
		String oPath = argumentForKey("-path", moArgs, 0);
		if (oPath == null) {
			oPath = clsGetARSPath.getConfigPath();
		}
		
		
	
		// read BW properties
		
		clsBWProperties oProp = clsBWProperties.readProperties(oPath, oFilename);
		
		if (oImplementationStagesFile != null) {
			//read implementation stages file
			clsBWProperties oPropImp = clsBWProperties.readProperties(oPath, oImplementationStagesFile);
			oPropImp.addPrefix(clsSimplePropertyLoader.P_DEFAULTSDECISIONUNIT+"."+eDecisionType.PA);
			//merge settings - overwrites exsiting entries
			oProp.putAll(oPropImp);
			
			oPropImp = clsBWProperties.readProperties(oPath, oImplementationStagesFile);
			oPropImp.addPrefix(clsSimplePropertyLoader.P_DEFAULTSDECISIONUNIT+"."+eDecisionType.ActionlessTestPA);
			//merge settings - overwrites exsiting entries
			oProp.putAll(oPropImp);
		}
		
		// show adapter if desired
		
		if (nAdapter) {
			@SuppressWarnings("unused")
			clsBWFastEntityAdapter oAdapterFrame = new clsBWFastEntityAdapter(null, "BWv1 - Fast Entity Adapter", oProp);
//			oAdapterFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			oAdapterFrame.setModal(true);
//			oAdapterFrame.setVisible(true);
		}
		
		// process config
		
		clsLoader oLoader = new clsSimplePropertyLoader(this, oProp);
		oLoader.loadObjects();
		
		//clear the charts
		moTestSeries.clear(); //TODO Clemens for charting
		//TODO clemens: add charts/statistics to schedule here 
	}

    static String argumentForKey(String key, String[] args, int startingAt)
    {
    	for(int x=0;x<args.length-1;x++)  // key can't be the last string
    		if (args[x].equalsIgnoreCase(key))
    			return args[x + 1];
    	return null;
    }
    
    static boolean keyExists(String key, String[] args, int startingAt)
    {
    	for(int x=0;x<args.length;x++)  // key can't be the last string
        	if (args[x].equalsIgnoreCase(key))
        		return true;
    	return false;
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
	@Deprecated
	public boolean getmbChartDisplay() {
		return mbChartDisplay;
	}
	
    /**
	 * @author deutsch
	 * 25.02.2009, 15:03:11
	 * 
	 * @return the moTestSeries
	 * @deprecated
	 */
	public XYSeries getMoTestSeries() {
		return moTestSeries;
	}	
}
