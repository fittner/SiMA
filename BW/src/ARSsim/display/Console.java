/**
 * 
 */
package ARSsim.display;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import sim.display.GUIState;

/**
 * @author langr
 *
 */
public class Console extends sim.display.Console {

	
	HashMap<Integer, ArrayList<JTabbedPane>> moTabbedPaneConfig = new HashMap<Integer, ArrayList<JTabbedPane>>();
	
	private int mnCurrentSelection = 0;
	private static int mnStandardTabs = 5; //How many TABS are always present? (0 is really zero)
	
	public Console(GUIState simulation) {
		super(simulation);
		// TODO Auto-generated constructor stub
	}
	
	public void addTabbSetup(Integer pnEntityType, ArrayList<JTabbedPane> poTabbedPanes)
	{
		moTabbedPaneConfig.put(pnEntityType, poTabbedPanes);
	}
	
	public void setView(Integer pnEntityType)
	{
		removeView(mnCurrentSelection);
		
		ArrayList<JTabbedPane> oSetTabSet = moTabbedPaneConfig.get(pnEntityType);
		if(oSetTabSet != null)
		{
			for(JTabbedPane oTab : moTabbedPaneConfig.get(pnEntityType))
			{
				getTabPane().addTab(oTab.getName(), oTab);
			}
		}

		mnCurrentSelection = pnEntityType;
	}
	
	private void removeView(Integer pnEntityType)
	{
		ArrayList<JTabbedPane> oRemoveTabSet = moTabbedPaneConfig.get(pnEntityType);
		if(oRemoveTabSet != null)
		{
			for(int i = 0; i < moTabbedPaneConfig.get(pnEntityType).size(); i++)
			{
				getTabPane().remove(mnStandardTabs);
			}
		}
	}

}
