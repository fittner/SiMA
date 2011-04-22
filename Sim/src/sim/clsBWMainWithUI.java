/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

package sim;

import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;

import statictools.clsGetARSPath;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.clsEventLoggerInspector;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
//import org.jfree.chart.ChartPanel;
import config.clsBWProperties;
import du.enums.eEntityType;
import bw.factories.clsSingletonProperties;
import bw.factories.clsSingletonMasonGetter;
//import bw.utils.visualization.clsCharts;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


/**
 * Main function for Simulation, UI part
 * 
 * @author muchitsch
 * 
 */
public class clsBWMainWithUI extends GUIState {
    public static final String P_BACKGROUNDCOLOR = "backgroundcolor";
    public static final String P_TITLE = "title";
    public static final String P_CLIPPING = "clipping";
    public static final String P_PORTRAYALTITLE = "portrayal_title";
    public static final String P_DRAWIMAGES = "draw_images";
    public static final String P_DRAWSENSORS = "draw_sensors";
    public static final String P_GAMEGRIDFRAMEVISIBLE = "gamegrid_visible";
    public static final String P_MAINWINDOWTITLE = "mainwindowtitle";
    
    public static final String  F_CONFIGFILENAME = "system.properties";
    
	/** GUI widget which holds some number of field portrayals and frames, 
	 * usually layered on top of one another */
	private ARSsim.display.Display2D moDisplay;
	/** window to hold Main Display2D panel */
	private JFrame moDisplayGamegridFrame;
	/** window to hold charting panel */
	private JFrame moChartFrame;
	/** responsible for drawing fields and letting the user manipulate 
	 * objects stored within them */
	private ContinuousPortrayal2D moGameGridPortrayal = new ContinuousPortrayal2D();
	/** holds all charts if charting is activated in startup */
	//private clsCharts moCharts = null;
	
	public clsBWMainWithUI(String[] args) { 
		super(new clsBWMain( System.currentTimeMillis(), args) ); 
	}
	
	//comment added by TD - do we need this constructor?
	//public clsBWMainWithUI(SimState poState) { super(poState); }	
	
	/**
	 * main which starts the whole simulation with a gui. takes one to two parameter.
	 * the first param can either be a number (see clsBWMain.start() to see which number loads
	 * which configuration. or it can be a filename pointing to the config.
	 * the second param can change the default config directory. see clsGetARSPath.getConfigPath() 
	 * for the used default path. 
	 *
	 * @author tobias
	 * Jul 26, 2009, 5:09:12 PM
	 *
	 * @param args
	 */
	public static void main(String[] args){
		String oPath = clsBWMain.argumentForKey("-path", args, 0);
		if (oPath == null) {
			oPath = clsGetARSPath.getConfigPath();
		}
		
		clsBWProperties oProp = clsBWProperties.readProperties(oPath, F_CONFIGFILENAME);
		clsSingletonProperties.setSystemProperties(oProp, P_DRAWIMAGES, P_DRAWSENSORS);
		
		clsBWMainWithUI oMainWithUI = new clsBWMainWithUI(args);
		clsSingletonMasonGetter.setConsole( new ARSsim.display.Console(oMainWithUI) );
		Dimension windowSize = clsSingletonMasonGetter.getConsole().getSize();
		windowSize.height+=400;
		clsSingletonMasonGetter.getConsole().setSize(windowSize);
		clsSingletonMasonGetter.getConsole().setVisible(true);
		
		clsEventLoggerInspector oELI = new clsEventLoggerInspector();
		clsSingletonMasonGetter.getConsole().getTabPane().addTab("Eventlog", oELI);
		clsEventLogger.setELI(oELI);
		//TODO: (langr) - for testing purpose only
		//oMainWithUI.testTabView();		
	}

	@Deprecated
	public void testTabView()
	{
		ArrayList<JTabbedPane> oTabView = new ArrayList<JTabbedPane> ();
		JTabbedPane oTestPaneBub1 = new JTabbedPane();
		oTestPaneBub1.setName("Bubble Tab 1");
		JTabbedPane oTestPaneBub2 = new JTabbedPane();
		oTestPaneBub2.setName("Bubble Tab 2");
		oTabView.add(oTestPaneBub1);
		oTabView.add(oTestPaneBub2);
		((ARSsim.display.Console)(clsSingletonMasonGetter.getConsole())).addTabbSetup(eEntityType.BUBBLE.hashCode(), oTabView);
		
		ArrayList<JTabbedPane> oTabView2 = new ArrayList<JTabbedPane> ();
		JTabbedPane oTestPaneBot1 = new JTabbedPane();
		oTestPaneBot1.setName("RemoteBot Tab 1");
		JTabbedPane oTestPaneBot2 = new JTabbedPane();
		oTestPaneBot2.setName("RemoteBot Tab 2");
		oTabView2.add(oTestPaneBot1);
		oTabView2.add(oTestPaneBot2);
		((ARSsim.display.Console)(clsSingletonMasonGetter.getConsole())).addTabbSetup(eEntityType.REMOTEBOT.hashCode(), oTabView2);
		
		
		((ARSsim.display.Console)(clsSingletonMasonGetter.getConsole())).setView(eEntityType.BUBBLE.hashCode());
		((ARSsim.display.Console)(clsSingletonMasonGetter.getConsole())).setView(eEntityType.REMOTEBOT.hashCode());
	}
	
	/** returns the title bar of the console
	 * @return String
	 */
	public static String getName() { 
    	clsBWProperties oProp = clsSingletonProperties.getSystemProperties();
    	String pre = "";
    	
		return oProp.getPropertyString(pre+P_MAINWINDOWTITLE); 
	} 

    KeyListener listener = new KeyListener() {
        @Override
		public void keyPressed(KeyEvent e) {
          dumpInfo("Pressed", e);
        }

        @Override
		public void keyReleased(KeyEvent e) {
          dumpInfo("Released", e);
        }

        @Override
		public void keyTyped(KeyEvent e) {
          dumpInfo("Typed", e);
        }

        private void dumpInfo(String s, KeyEvent e) {
/*        	
          System.out.println(s);
          int code = e.getKeyCode();
          System.out.println("\tCode: " + KeyEvent.getKeyText(code));
          System.out.println("\tChar: " + e.getKeyChar());
          int mods = e.getModifiersEx();
          System.out.println("\tMods: "
              + KeyEvent.getModifiersExText(mods));
          System.out.println("\tLocation: "
              + location(e.getKeyLocation()));
          System.out.println("\tAction? " + e.isActionKey());
*/          
        }

/*	// EH - make warning free
        private String location(int location) {
          switch (location) {
          case KeyEvent.KEY_LOCATION_LEFT:
            return "Left";
          case KeyEvent.KEY_LOCATION_RIGHT:
            return "Right";
          case KeyEvent.KEY_LOCATION_NUMPAD:
            return "NumPad";
          case KeyEvent.KEY_LOCATION_STANDARD:
            return "Standard";
          case KeyEvent.KEY_LOCATION_UNKNOWN:
          default:
            return "Unknown";
          }
        }
*/
      };
	

     
     public static clsBWProperties getDefaultProperties(String poPrefix) {
    	 String pre = clsBWProperties.addDot(poPrefix);
    	 
    	 clsBWProperties oProp = ARSsim.display.Display2D.getDefaultProperties(pre);
    	 
    	 oProp.setProperty(pre+P_BACKGROUNDCOLOR, Color.white);
    	 oProp.setProperty(pre+P_TITLE, "BW V1.0 GameGrid");
    	 oProp.setProperty(pre+P_CLIPPING, false);
    	 oProp.setProperty(pre+P_PORTRAYALTITLE, "BW GameGrid");
    	 oProp.setProperty(pre+P_DRAWIMAGES, true);
    	 oProp.setProperty(pre+P_GAMEGRIDFRAMEVISIBLE, true);
    	 oProp.setProperty(pre+P_MAINWINDOWTITLE, "BW V1.0");
    	 oProp.setProperty(pre+P_DRAWSENSORS, true);
    	 
    	 return oProp;
     }
      
    @Override
	public void init(Controller poController){
    	super.init(poController);
		
    	clsBWProperties oProp = clsSingletonProperties.getSystemProperties();
    	String pre = "";
    	
		moDisplay = ARSsim.display.Display2D.createDisplay2d("", oProp, this);
		moDisplay.setClipping( oProp.getPropertyBoolean(pre+P_CLIPPING) ); //we�d like to see objects outside the width & height box
		
		//let the display generate a frame for you
		moDisplayGamegridFrame = moDisplay.createFrame();
		moDisplayGamegridFrame.setTitle( oProp.getPropertyString(pre+P_TITLE) );
		moDisplayGamegridFrame.addKeyListener(listener);
		poController.registerFrame(moDisplayGamegridFrame); //register the JFrame with the Console to include it in the Console�s list
		
		// specify the backdrop color  -- what gets painted behind the displays
		moDisplay.setBackdrop( oProp.getPropertyColor(pre+P_BACKGROUNDCOLOR) );
		moDisplayGamegridFrame.setVisible( oProp.getPropertyBoolean(pre+P_GAMEGRIDFRAMEVISIBLE) );
		moDisplay.attach(moGameGridPortrayal, oProp.getPropertyString(pre+P_PORTRAYALTITLE) ); //attach the Portrayal to the Display2D to display it 
		
		clsSingletonMasonGetter.setDisplay2D(moDisplay);
	
		//add the charting panel
		if ( ((clsBWMain)state).getmbChartDisplay()) {
	        addChartPanel(poController,(clsBWMain)state);
		}
	}
	
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
	
    @Override
	public void start(){
		super.start();
		setupPortrayals();
	}
	
    @Override
	public void load(SimState poState){
		super.load(poState);
		setupPortrayals();
	}
	
	/** Here we tell the Portrayal which field it is portraying and how it�s doing it. 
	 * We�ll also reset the display so it reregisters itself with the console in 
	 * preparation for being stepped each timestep 
	 */
	public void setupPortrayals(){
				
		// tell the portrayals what to portray and how to portray them = connection between field and portrayal
		moGameGridPortrayal.setField(clsSingletonMasonGetter.getFieldEnvironment());
		
		moDisplay.reset();
//		
		// redraw the display
		moDisplay.repaint();
	}
	
	@Override
	public Object getSimulationInspectedObject(){ 
		return state; 
	}
	
	@Override
	public Inspector getInspector(){
		//Override to get constantly updating inspectors = volatile
	    Inspector oInspector = super.getInspector();
	    oInspector.setVolatile(true);
	    return oInspector;
    }
	
	
	
	
	/**
	 * Adds the seperate charting panel if option is set to true in startup.
	 * Creates the panel, adds the charts.
	 * When Changes in clsCharts are done, you have to add some lines here too!
	 *
	 * @param poController
	 * @param poMainModelClass
	 */
	@Deprecated
	public void addChartPanel( Controller poController, clsBWMain poMainModelClass ) {
		
		// add all charts from clsCharts
		// FIXME - are these chart panels still needed? clsCharts has been deleted!
//		moCharts = new clsCharts(poMainModelClass);
//		ChartPanel oTestPanel = null;
		
		//create charts
		if (true) { // TODO clemens: do we want to ad this chart? maybe read from config file? for testing.. always yes
			// FIXME - are these chart panels still needed? clsCharts has been deleted!
//			oTestPanel = new ChartPanel(moCharts.createTestChart());
        }
		
		// create the chart frame
        moChartFrame = new JFrame();
        moChartFrame.setResizable(true);
        Container moContentpanel = moChartFrame.getContentPane();
        moContentpanel.setLayout(new BoxLayout(moContentpanel, BoxLayout.Y_AXIS));
	    
        
     
        // register frame and show the window
        moChartFrame.setTitle("Live Bubble Statistics");
        moChartFrame.pack();
        // register the chartFrame so it appears in the "Display" list
        poController.registerFrame(moChartFrame);
        // make the frame visible
        moChartFrame.setVisible(true);
   }
	
}
