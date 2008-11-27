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
import sim.portrayal.simple.*;
import sim.portrayal.continuous.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import org.jfree.chart.ChartPanel;

/**
 * Main function for Simulation, UI part
 * 
 * @author muchitsch
 * 
 */
public class clsBWMainWithUI extends GUIState{

	/** GUI widget which holds some number of field portrayals, 
	 * usually layered on top of one another
	 */
	public Display2D moDisplay;
	/** window to hold Display2D
	 */
	public JFrame moDisplayFrame;
	public JFrame moChartFrame;
	/** responsible for drawing fields and letting the user manipulate 
	 * objects stored within them 
	 */
	ContinuousPortrayal2D moGameGridPortrayal = new ContinuousPortrayal2D();
	/**
	 * holds all charts if charting is activated in startup
	 */
	public clsCharts moCharts;
	
	
	public static void main(String[] poArgs){
		Console oConsole = new Console(new clsBWMainWithUI());
		oConsole.setVisible(true);
	}
	public clsBWMainWithUI() { super(new clsBWMain( System.currentTimeMillis())); }
	public clsBWMainWithUI(SimState poState) { super(poState); }
	
	/** returns the title bar of the console
	 * @return String
	 */
	public static String getName() { return "... bw V3.0 ..."; } 

	public void init(Controller poController){
		
		super.init(poController);
		
		moDisplay = new Display2D(600,600,this,1); //TODO make me konfiguierbar
		moDisplay.setClipping(false);
		
		moDisplayFrame = moDisplay.createFrame();
		moDisplayFrame.setTitle("bw V3.0 Display");
		
		poController.registerFrame(moDisplayFrame);
		// specify the backdrop color  -- what gets painted behind the displays
		moDisplay.setBackdrop(Color.white); //TODO make me konfigurierbar
		moDisplayFrame.setVisible(true);
		moDisplay.attach(moGameGridPortrayal, "Arena");
		
		if ( ((clsBWMain)state).mbChartDisplay) {
	        addChartPanel(poController,(clsBWMain)state);
		}
	}
	
	public void quit(){
		super.quit();
		if (moDisplayFrame!=null) 
			moDisplayFrame.dispose();
		moDisplayFrame = null;
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
				
		// tell the portrayals what to portray and how to portray them
		moGameGridPortrayal.setField(((clsBWMain)state).moGameGridField);
		
		
		moDisplay.reset();
		
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
