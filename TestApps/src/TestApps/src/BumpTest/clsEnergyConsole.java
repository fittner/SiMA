package TestApps.src.BumpTest;


import javax.swing.JComponent;

import sim.display.Console;
import sim.display.GUIState;
import sim.util.gui.HTMLBrowser;

public class clsEnergyConsole extends Console{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsEnergyConsole(GUIState simulation) {
		super(simulation);
		
		JComponent infoPanel = new HTMLBrowser(GUIState.getInfo(simulation.getClass()));
        getTabPane().addTab("EnergyInspector", infoPanel);

	}

	
	
}
