/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - removed keylistener. not used by anything.
 * 2011/06/20 TD - added some javadoc
 * 2011/06/20 TD - removed the gamegrid_visible parameter in the properties file.
 */

package sim;

import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.portrayal.simple.OvalPortrayal2D;
import java.awt.Color;
import java.awt.Dimension;


import javax.swing.JFrame;

import complexbody.io.sensors.datatypes.clsInspectorPerceptionItem;

import properties.clsProperties;


import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import singeltons.clsSingletonMasonGetter;
import singeltons.clsSingletonProperties;
import utils.clsGetARSPath;




/**
 * Main function for Simulation, UI part
 *
 * Params for main(args[]):<ul>
 *  <li>-config filename - defines which property file for the simulation config to load. default for filename = "testsetup.main.properties".</li>
 *  <li>-impstages filename - defines which property file for selecting the different implementation stages of the various modules to be loaded. If nothing provided, the default defined in the class is loaded (usually imp_basic).</li>
 *  <li>-adapter true/false - if set to true the fast entity adapter (@see clsBWFastEntityAdapter) is loaded. If not present or false, the property file is loaded without changes.</li>
 *  <li>-path direcotry - sets the directory where the property files are stored to the given path. default is defined by clsGetARSPath.getConfigPath().</li>
 *  <li>-autostart true/false - if set to true, the simulation will be started and paused at step 0. the effect is that the whole world has been created. otherwise the simulation is not started. in this case the gui is created only.
 *  </ul>
 *   
 * @author muchitsch
 * @see clsMain
 */
public class SimulatorMain extends GUIState {
	
    /** background color of the game window */
    public static final String P_BACKGROUNDCOLOR = "backgroundcolor";
    /** title of the game window */
    public static final String P_TITLE = "title";
    /** clipping of objects outside the gamw window */
    public static final String P_CLIPPING = "clipping";
    /** title of the portrayal. currently not displayed */
    public static final String P_PORTRAYALTITLE = "portrayal_title";
    /** toggle display of overlay images on/off. */
    public static final String P_DRAWIMAGES = "draw_images";
    /** draw the range of the external sensors. */
    public static final String P_DRAWSENSORS = "draw_sensors";
    /** The title to be displayed in the tab/inspector console. */
    public static final String P_MAINWINDOWTITLE = "mainwindowtitle";
    /** turn logging to file on-off */
    public static final String P_USELOGGER = "useLogger";
    /** turn system wide anti aliasing in inspectors on or off **/
    public static final String P_ANTIALIASING_BASE_SETTING = "antialiasing_base_setting";

    /** filename of the system properties file. contains all the P_* params defined in this class. */
    
    public static final String  F_CONFIGFILENAME = "system.properties";
    
    public static boolean mbHideGui = false;
    
	/** GUI widget which holds some number of field portrayals and frames, 
	 * usually layered on top of one another */
	private display.Display2D moDisplay;
	/** window to hold Main Display2D panel */
	private JFrame moDisplayGamegridFrame;
	/** window to hold charting panel */
	private JFrame moChartFrame;
	/** responsible for drawing fields and letting the user manipulate 
	 * objects stored within them */
	private ContinuousPortrayal2D moGameGridPortrayal = new ContinuousPortrayal2D();
	
	FastValueGridPortrayal2D moArousalGridPortrayal = new FastValueGridPortrayal2D("Sensor Arousal Grid");
	
	NetworkPortrayal2D moTPMNetworkPortrayal = new NetworkPortrayal2D();
	private ContinuousPortrayal2D moTPMNodePortrayal = new ContinuousPortrayal2D();
	


	
	/**
	 * Creates an instance of clsBWMain with the current time in milliseconds as seed for the random number generator and the provided args as parameters.
	 *
	 * @since 20.06.2011 18:06:02
	 *
	 * @param args
	 * @see clsMain
	 * @see SimulatorMain#main(java.lang.String[])
	 */
	public SimulatorMain(String[] args) { 
		super(new clsMain( System.currentTimeMillis(), args) ); 
	}
	
	public static void hideGui(boolean pbHideGui) {
		mbHideGui = pbHideGui;
	}
	
	/**
	 * This method creates the whole simulation including the GUI. An instance of clsBWMainWithUI is created with the given params. Further, the 
	 * console with the tabs and inspectors is created.
	 *
	 * Params for main(args[]):<ul>
 	 *  <li>-config filename - defines which property file for the simulation config to load. default for filename = "testsetup.main.properties".</li>
 	 *  <li>-impstages filename - defines which property file for selecting the different implementation stages of the various modules to be loaded. If nothing provided, the default defined in the class is loaded (usually imp_basic).</li>
 	 *  <li>-adapter true/false - if set to true the fast entity adapter (@see clsBWFastEntityAdapter) is loaded. If not present or false, the property file is loaded without changes.</li>
 	 *  <li>-path direcotry - sets the directory where the property files are stored to the given path. default is defined by clsGetARSPath.getConfigPath().</li>
 	 *  <li>-autostart true/false - if set to true, the simulation will be started and paused at step 0. the effect is that the whole world has been created. otherwise the simulation is not started. in this case the gui is created only.
 	 *  </ul>
	 *
	 * @author deutsch
	 * Jul 26, 2009, 5:09:12 PM
	 *
	 * @see clsMain
	 * @param args
	 */
	public static void main(String[] args){
		//IMPORTANT: do not change anything here just to run the configuration
		//you like! you can change the command line arguments easily:
		//  1. right click on clsBWMainWithUI.java in the package explorer.
		//  2. select run as or debug as (they have different configs)
		//  3. select run or debug config
		//  4. select the second tab "arguments"
		//  5. enter either the selected index or the filename into the textbox program arguments
		//  6. apply
		//  7. start debuggin / running
		
		
		
		String oPath = clsMain.argumentForKey("-path", args, 0);
		if (oPath == null) {
			oPath = clsGetARSPath.getConfigPath();
		}
		
		mbHideGui = new Boolean(clsMain.argumentForKey("-hidegui", args, 0));
		
		clsProperties oProp = clsProperties.readProperties(oPath, F_CONFIGFILENAME);
		clsSingletonProperties.setSystemProperties(oProp, P_DRAWIMAGES, P_DRAWSENSORS, P_USELOGGER);
		
		SimulatorMain oMainWithUI = new SimulatorMain(args);
		
		clsSingletonMasonGetter.setConsole( new console.Console(oMainWithUI, true) ); // 2011/06/14 CM+TD: adapted to new ARSsim.display.Console constructor
		Dimension windowSize = clsSingletonMasonGetter.getConsole().getSize();
		windowSize.height+=300;
		clsSingletonMasonGetter.getConsole().setSize(windowSize);
		clsSingletonMasonGetter.getConsole().setVisible(!mbHideGui);

		if(new Boolean(clsMain.argumentForKey("-autostart", args, 0))) {
			clsSingletonMasonGetter.getConsole().pressPause();
		}

		//clsEventLoggerInspector oELI = new clsEventLoggerInspector();
		//clsSingletonMasonGetter.getConsole().getTabPane().addTab("Eventlog", oELI);
		//clsEventLogger.setELI(oELI);
		
		//TODO CM load the properties Inspector Tab here
		//clsPropertiesInspector oMagnumPI = new clsPropertiesInspector();
		//clsSingletonMasonGetter.getConsole().getTabPane().addTab("PropertyInspector", oMagnumPI);
		//oMagnumPI.setPropertyObjecttoShowHere(oProp);

	}

	
	/** returns the title of the console
	 * @return String
	 */
	public static String getName() { 
    	clsProperties oProp = clsSingletonProperties.getSystemProperties();
    	String pre = "";
    	
		return oProp.getPropertyString(pre+P_MAINWINDOWTITLE); 
	} 
    
     /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     */
    public static clsProperties getDefaultProperties(String poPrefix) {
    	 String pre = clsProperties.addDot(poPrefix);
    	 
    	 clsProperties oProp = display.Display2D.getDefaultProperties(pre);
    	 
    	 oProp.setProperty(pre+P_BACKGROUNDCOLOR, Color.white);
    	 oProp.setProperty(pre+P_TITLE, "ARSin V1.0 GameGrid");
    	 oProp.setProperty(pre+P_CLIPPING, false);
    	 oProp.setProperty(pre+P_PORTRAYALTITLE, "ARSin GameGrid");
    	 oProp.setProperty(pre+P_DRAWIMAGES, true);
    	 oProp.setProperty(pre+P_MAINWINDOWTITLE, "ARSin V1.0");
    	 oProp.setProperty(pre+P_DRAWSENSORS, true);
    	 oProp.setProperty(pre+P_USELOGGER, false);
    	 
    	 
    	 return oProp;
     }
    
    /** Called to initialize (display) windows etc. 
    You can use this to set up the windows, then register them with the Controller so it can manage
    hiding, showing, and moving them. */
    @Override
	public void init(Controller poController){
    	super.init(poController);
		
    	clsProperties oProp = clsSingletonProperties.getSystemProperties();
    	String pre = "";
    	
		moDisplay = display.Display2D.createDisplay2d("", oProp, this);
		moDisplay.setClipping( oProp.getPropertyBoolean(pre+P_CLIPPING) ); //we'd like to see objects outside the width & height box
		
		//let the display generate a frame for you
		moDisplayGamegridFrame = moDisplay.createFrame();
		moDisplayGamegridFrame.setTitle( oProp.getPropertyString(pre+P_TITLE) );
		poController.registerFrame(moDisplayGamegridFrame); //register the JFrame with the Console to include it in the Console�s list
		
		// specify the backdrop color  -- what gets painted behind the displays
		moDisplay.setBackdrop( oProp.getPropertyColor(pre+P_BACKGROUNDCOLOR) );
		moDisplayGamegridFrame.setVisible( !mbHideGui );
		moDisplay.attach(moGameGridPortrayal, oProp.getPropertyString(pre+P_PORTRAYALTITLE) ); //attach the Portrayal to the Display2D to display it 
		
		//add the arousal Grid, in case of problems, outcomment the following line and it wont be there
		moDisplay.attach(moArousalGridPortrayal, "arousal perception");
		
		moDisplay.attach(moTPMNetworkPortrayal, "TPM Network Portrayal");
		moDisplay.attach(moTPMNodePortrayal, "TPM Node Portrayal");
		
		clsSingletonMasonGetter.setDisplay2D(moDisplay);
		
		clsSingletonProperties.setAntiAliasing(oProp.getPropertyBoolean(pre+P_ANTIALIASING_BASE_SETTING));
		
		// add another window for test
		//Display2D test = new Display2D(300, 300,this, 22);
		//test.setVisible(true);
		//poController.registerFrame(test.createFrame()); 
		//clsSingletonMasonGetter.setDisplay2D(test);
	}
    
    /** Called by the Console when the user is quitting the SimState.  A good place
    to stick stuff that you'd ordinarily put in a finalizer -- finalizers are
    tenuous at best. So here you'd put things like the code that closes the relevant
    display windows etc.*/	
    @Override
	public void quit(){
		super.quit();
		//remove game grid
		if (moDisplayGamegridFrame!=null) 
			moDisplayGamegridFrame.dispose();
		moDisplayGamegridFrame = null;
		
		//remove charting
		if (moChartFrame!=null) 
			moChartFrame.dispose();
		moChartFrame = null;
		
		moDisplay = null;
	}
	
    /** Called immediately prior to starting the simulation, or in-between
    simulation runs.  Ordinarily, you wouldn't need to override this hook. */    
    @Override
	public void start(){
		super.start();
		setupPortrayals();
	}
	
    /** Called by the Console when the user is loading in a new state from a checkpoint.  The
    new state is passed in as an argument.  The default version simply calls finish(),
    then sets this.state to the new state.  You should override this, calling super.load(state) first, 
    to reset your portrayals etc. to reflect the new state.
    state.start() will NOT be called.  Thus anything you handled in start() that needs
    to be reset to accommodate the new state should be handled here.  We recommend that you 
    call repaint() on any Display2Ds. */    
    @Override
	public void load(SimState poState){
		super.load(poState);
		setupPortrayals();
	}
	
	/** Here we tell the Portrayal which field it is portraying and how it�s doing it. 
	 * We'll also reset the display so it reregisters itself with the console in 
	 * preparation for being stepped each timestep 
	 */
	public void setupPortrayals(){
				
		// tell the portrayals what to portray and how to portray them = connection between field and portrayal
		moGameGridPortrayal.setField(clsSingletonMasonGetter.getFieldEnvironment());
	
		//add a arousal layer portrayal
		moArousalGridPortrayal.setField(clsSingletonMasonGetter.getArousalGridEnvironment());
		moArousalGridPortrayal.setMap(new sim.util.gui.SimpleColorMap(0,1,new Color(0,0,0,0),new Color(255,165,0,255)));
		
		//set up TPM network portrayal
		moTPMNodePortrayal.setField(clsSingletonMasonGetter.getTPMNodeField());
		moTPMNodePortrayal.setPortrayalForAll(
	                        new OvalPortrayal2D(5)
	                            {
								private static final long serialVersionUID = 1L;
								@Override
								public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
	                                {
	                            	clsInspectorPerceptionItem oItem = (clsInspectorPerceptionItem)object;

	                                //int agitationShade = (int) (student.getAgitation() * 255 / 10.0);
	                                //if (agitationShade > 255) agitationShade = 255;
	                                //paint = new Color(agitationShade, 0, 255 - agitationShade);
	                            	paint = new Color(0, 255, 0, 25);
	                                super.draw(object, graphics, info);
	                                }
	                            } 
                    );
		
		moTPMNetworkPortrayal.setField( new SpatialNetwork2D( clsSingletonMasonGetter.getTPMNodeField(), clsSingletonMasonGetter.getTPMNetworkField() ) );
		moTPMNetworkPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
 
		moDisplay.reset();
		
		// redraw the display
		moDisplay.repaint();
	}
	
	
    /** Returns an object with various property methods (getFoo(...), isFoo(...), setFoo(...)) whose
    properties will be accessible by the user.  This gives you an easy way to allow the user to
    set certain global properties of your model from the GUI.   If null is returned (the default),
    then nothing will be displayed to the user.  One trick you should know about your object:
    it should be public, as well its property methods, and if it's anonymous, it should not
    introduce any property methods not defined in its superclass.  Otherwise Java's reflection
    API can't access those methods -- they're considered private.  GUIState also supports
    sim.util.Properties's domFoo(...) domain declarations to allow for sliders and pop-up lists.*/	
	@Override
	public Object getSimulationInspectedObject(){ 
		return state; 
	}
	
    /** By default returns a non-volatile Inspector which wraps around
    getSimulationInspectedObject(); if getSimulationInspectedObject() returns null, then getInspector()
    will return null also.  Override this to provide a custom inspector as you see fit.  */	
	@Override
	public Inspector getInspector(){
		//Override to get constantly updating inspectors = volatile
	    Inspector oInspector = super.getInspector();
	    oInspector.setVolatile(true);
	    return oInspector;
    }
	
	public static void hideField(boolean pbHide) {
//		if(pbHide) {
//			moPl
//		}
	}
}
