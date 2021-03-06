/**

 * CHANGELOG
 *
 * 2011/06/20 TD - removed deprecated methods
 * 2011/06/20 TD - added some javadoc
 */

package sim;

import properties.clsProperties;
import loader.clsLoader;
import loader.clsSimplePropertyLoader;
import du.enums.eDecisionType;
import ec.util.MersenneTwisterFast;
import sim.engine.Schedule;
import sim.engine.SimState;
import singeltons.clsSimState;
import singeltons.clsSingletonProperties;
import utils.clsGetARSPath;


/**
 * Main function for simulation (not to be called directly). Needs to be called by clsBWMainWithUI. The physics engine and some other tools
 * need the shape of the entities. Thus, if started without a GUI, the entities have no shape and the 2 dimensional world will not be populated.
 * 
 * @see SimulatorMain
 * @author muchitsch
 */
public class clsMain extends SimState{
	private static final long serialVersionUID = -1952879483933572186L;
	
	/** stores the runtime arguements. set by method main */
	private String[] moArgs;
     
    /**
     * Creates an instance of the simulation with the given seed. No agents have been created yet. This is done in method start(). 
     *
     * @since 20.06.2011 17:25:22
     *
     * @param pnSeed - seed for the random number generator
     * @param args - runtime arguments
     */
    public clsMain(long pnSeed, String[] args) {
    	super(new MersenneTwisterFast(pnSeed), new Schedule());
    	moArgs = args;
    	clsSimState.setSimState(this);
    }
    
	/**
	 * start is the method called when the simulation starts but before any agents have been pulsed.
	 * 
	 * @since 20.06.2011 17:28:33
	 * 
	 * @see sim.engine.SimState#start()
	 */
	@Override
	public void start()	{
		super.start();
		
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
					case 7: oFilename = "AW.survivalworld.properties"; break;
					case 8: oFilename = "one_bubble_one_cake_one_staticbubble.properties"; break;
					case 9: oFilename = "TD_PhD.main.properties"; break;
					//case 10: oFilename = "AW.pa.pausecase.properties"; break;
					case 10: oFilename = "AW.PhD.Usecase.PAUsecase1ObstacleModification.properties"; break;
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
		String oScenarioPath = clsGetARSPath.getScenarioPath();
		if (oPath == null) {
			oPath = clsGetARSPath.getConfigPath();
		}
		
		

		// read BW properties
		
		clsProperties oProp = clsProperties.readProperties(oScenarioPath, oFilename);
		
		if (oImplementationStagesFile != null) {
			//read implementation stages file
			clsProperties oPropImp = clsProperties.readProperties(oPath, oImplementationStagesFile);
			oPropImp.addPrefix(clsSimplePropertyLoader.P_DEFAULTSDECISIONUNIT+"."+eDecisionType.PA);
			//merge settings - overwrites exsiting entries
			oProp.putAll(oPropImp);
			
			oPropImp = clsProperties.readProperties(oPath, oImplementationStagesFile);
			oPropImp.addPrefix(clsSimplePropertyLoader.P_DEFAULTSDECISIONUNIT+"."+eDecisionType.ActionlessTestPA);
			
			//merge settings - overwrites exsiting entries
			oProp.putAll(oPropImp);
		}
			
		// process config
		
		clsLoader oLoader = new clsSimplePropertyLoader(this, oProp);
		oLoader.loadObjects();
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
    
    public void setShowArousalGrid(boolean value){
    	clsSingletonProperties.setShowArousalGridPortrayal(value);
    }
    
    public boolean getShowArousalGrid(){
    	return clsSingletonProperties.showArousalGridPortrayal();
    }
    
    public void setShowTPMNetworkGrid(boolean value){
    	clsSingletonProperties.setShowTPMNetworkGrid(value);
    }
    
    public boolean getShowTPMNetworkGrid(){
    	return clsSingletonProperties.showTPMNetworkGrid();
    }
    
    public void setShowSpeechAnswerOverlay(boolean value){
    	clsSingletonProperties.setShowSpeechAnswerOverlay(value);
    }
    public boolean getShowSpeechAnswerOverlay(){
    	return clsSingletonProperties.showSpeechAnswerOverlay();
    }
       
    public void setShowSpeechQuestionOverlay(boolean value){
    	clsSingletonProperties.setShowSpeechQuestionOverlay(value);
    }
    
    public boolean getShowSpeechQuestionOverlay(){
    	return clsSingletonProperties.showSpeechQuestionOverlay();
    }
    
    public void setShowLifeIndicator(boolean value){
    	clsSingletonProperties.setShowLifeIndicator(value);
    }
    public boolean getShowLifeIndicator(){
    	return clsSingletonProperties.showLifeIndicator();
    }
    
    public void setShowFacialExpressionOverlay(boolean value){
    	clsSingletonProperties.setShowFacialExpressionOverlay(value);
    }
    public boolean getShowFacialExpressionOverlay(){
    	return clsSingletonProperties.showFacialExpressionOverlay();
    }
    
    public void setTimeChartAntiAliasing(boolean value){
    	clsSingletonProperties.setAntiAliasing(value);
    }
    public boolean getTimeChartAntiAliasing(){
    	return clsSingletonProperties.isAntiAliasing();
    }
    
    public void setShowBodilyExpressions(boolean value){
    	clsSingletonProperties.setShowBodilyExpressions(value);
    }
    public boolean getShowBodilyExpressions(){
    	return clsSingletonProperties.showBodilyExpressions();
    }
 }
