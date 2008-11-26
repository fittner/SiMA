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
public class clsMainWithUI extends GUIState{

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
	ContinuousPortrayal2D mobwArena = new ContinuousPortrayal2D();
	public clsCharts moCharts;
	
	
	
	public static void main(String[] args){
		Console oConsole = new Console(new clsMainWithUI());
		oConsole.setVisible(true);
	}
	public clsMainWithUI() { super(new clsMain( System.currentTimeMillis())); }
	public clsMainWithUI(SimState oState) { super(oState); }
	
	/** returns the title bar of the console
	 * @return String
	 */
	public static String getName() { return "... bw V3.0 ..."; } 

	public void init(Controller oController){
		
		super.init(oController);
		
		moDisplay = new Display2D(600,600,this,1); //TODO make me konfiguierbar
		moDisplay.setClipping(false);
		
		moDisplayFrame = moDisplay.createFrame();
		moDisplayFrame.setTitle("bw V3.0 Display");
		
		oController.registerFrame(moDisplayFrame);
		// specify the backdrop color  -- what gets painted behind the displays
		moDisplay.setBackdrop(Color.black); //TODO make me konfigurierbar
		moDisplayFrame.setVisible(true);
		moDisplay.attach(mobwArena, "Arena");
		
		if ( ((clsMain)state).mbChart_display) {
	        addChartPanel(oController,(clsMain)state);
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
	
	public void load(SimState oState){
		super.load(oState);
		setupPortrayals();
	}
	
	/** Here we tell the Portrayal which field it is portraying and how it�s doing it. 
	 * We�ll also reset the display so it reregisters itself with the console in 
	 * preparation for being stepped each timestep 
	 */
	public void setupPortrayals(){
		
		//TODO insert portrayal definitions here, best in a method of world generation...
		
		
		
		
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
	
	void addChartPanel( Controller oController, clsMain model ) {
		
		// TODO add charts
		moCharts = new clsCharts(model);
		ChartPanel moTestPanel = null;
		
		//TODO create charts
		if (true) { // abfrage ob wir das chart haben wollen, zb config
			moTestPanel = new ChartPanel(moCharts.createTestChart());
        }
		
		// create the chart frame
        moChartFrame = new JFrame();
        moChartFrame.setResizable(true);
        Container moContentpanel = moChartFrame.getContentPane();
        moContentpanel.setLayout(new BoxLayout(moContentpanel, BoxLayout.Y_AXIS));
	    
        
        
        //TODO add charts to panel
        if (moTestPanel!=null) {
        	moContentpanel.add(moTestPanel);
        	moContentpanel.add(Box.createRigidArea(new Dimension(0, 6)));
        }
          
        
        
        moChartFrame.setTitle("Live Bubble Statistics");
        moChartFrame.pack();
        // register the chartFrame so it appears in the "Display" list
        oController.registerFrame(moChartFrame);
        // make the frame visible
        moChartFrame.setVisible(true);
        
    }
	
}
