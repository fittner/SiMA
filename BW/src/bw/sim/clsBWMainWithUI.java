/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

package bw.sim;

import sim.display.*;
import sim.engine.*;
import javax.swing.*;
import sim.portrayal.*;
import sim.portrayal.continuous.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import org.jfree.chart.ChartPanel;

import bw.factories.clsSingletonMasonGetter;
import bw.utils.visualization.clsCharts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Main function for Simulation, UI part
 * 
 * @author muchitsch
 * 
 */
public class clsBWMainWithUI extends GUIState{

	/** GUI widget which holds some number of field portrayals and frames, 
	 * usually layered on top of one another */
	private Display2D moDisplay;
	/** window to hold Main Display2D panel */
	private JFrame moDisplayGamegridFrame;
	/** window to hold charting panel */
	private JFrame moChartFrame;
	/** responsible for drawing fields and letting the user manipulate 
	 * objects stored within them */
	private ContinuousPortrayal2D moGameGridPortrayal = new ContinuousPortrayal2D();
	/** holds all charts if charting is activated in startup */
	private clsCharts moCharts = null;
	
	//CTORs
	public clsBWMainWithUI() { super(new clsBWMain( System.currentTimeMillis())); }
	public clsBWMainWithUI(SimState poState) { super(poState); }	
	
	public static void main(String[] poArgs){
		//console is an elaborate GUI Controller. This is the standard way of starting the UI.
		Console oConsole = new Console(new clsBWMainWithUI());
		oConsole.setVisible(true);
	}
	
	/** returns the title bar of the console
	 * @return String
	 */
	public static String getName() { return "... BW V3.0 ..."; } 

    KeyListener listener = new KeyListener() {
        public void keyPressed(KeyEvent e) {
          dumpInfo("Pressed", e);
        }

        public void keyReleased(KeyEvent e) {
          dumpInfo("Released", e);
        }

        public void keyTyped(KeyEvent e) {
          dumpInfo("Typed", e);
        }

        private void dumpInfo(String s, KeyEvent e) {
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
        }

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
      };
	
	public void init(Controller poController){
		
		super.init(poController);
		
		moDisplay = new Display2D(600,600,this,1); //TODO make me konfiguierbar
		moDisplay.setClipping(false); //we’d like to see objects outside the width & height box
		
		//let the display generate a frame for you
		moDisplayGamegridFrame = moDisplay.createFrame();
		moDisplayGamegridFrame.setTitle("BW V3.0 GameGrid");
		moDisplayGamegridFrame.addKeyListener(listener);
		poController.registerFrame(moDisplayGamegridFrame); //register the JFrame with the Console to include it in the Console’s list
		
		// specify the backdrop color  -- what gets painted behind the displays
		moDisplay.setBackdrop(Color.white); //TODO make me konfigurierbar
		moDisplayGamegridFrame.setVisible(true);
		moDisplay.attach(moGameGridPortrayal, "BW GameGrid"); //attach the Portrayal to the Display2D to display it 
		
		//add the charting panel
		if ( ((clsBWMain)state).getmbChartDisplay()) {
	        addChartPanel(poController,(clsBWMain)state);
		}
	}
	
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
	
	public void start(){
		super.start();
		setupPortrayals();
	}
	
	public void load(SimState poState){
		super.load(poState);
		setupPortrayals();
	}
	
	/** Here we tell the Portrayal which field it is portraying and how it’s doing it. 
	 * We’ll also reset the display so it reregisters itself with the console in 
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
	
	public Object getSimulationInspectedObject(){ 
		return state; 
	}
	
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
	public void addChartPanel( Controller poController, clsBWMain poMainModelClass ) {
		
		// add all charts from clsCharts
		moCharts = new clsCharts(poMainModelClass);
		ChartPanel oTestPanel = null;
		
		//create charts
		if (true) { // TODO clemens: do we want to ad this chart? maybe read from config file? for testing.. always yes
			oTestPanel = new ChartPanel(moCharts.createTestChart());
        }
		
		// create the chart frame
        moChartFrame = new JFrame();
        moChartFrame.setResizable(true);
        Container moContentpanel = moChartFrame.getContentPane();
        moContentpanel.setLayout(new BoxLayout(moContentpanel, BoxLayout.Y_AXIS));
	    
        
        
        //add charts to panel
        if (oTestPanel!=null) {
        	moContentpanel.add(oTestPanel);
        	moContentpanel.add(Box.createRigidArea(new Dimension(0, 6)));
        }
          
        
        // register frame and show the window
        moChartFrame.setTitle("Live Bubble Statistics");
        moChartFrame.pack();
        // register the chartFrame so it appears in the "Display" list
        poController.registerFrame(moChartFrame);
        // make the frame visible
        moChartFrame.setVisible(true);
   }
	
}
