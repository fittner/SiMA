/**
 * 2011/06/14: CM+TD - added "pressPause()" to constructor. now the simulation loads everything upon start and is in paused mode afterwards if the corresponding booelan param of the constructor is set to true.
 */
package ARSsim.display;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import sim.display.GUIState;

/**
 * @author langr
 *
 * 
 */
public class Console extends sim.display.Console {

	private static final long serialVersionUID = 7217845510506158250L;

	HashMap<Integer, ArrayList<JTabbedPane>> moTabbedPaneConfig = new HashMap<Integer, ArrayList<JTabbedPane>>();
	
	private int mnCurrentSelection = 0;
	private static int mnStandardTabs = 5; //How many TABS are always present? (0 is really zero)
	
	public Console(GUIState simulation, boolean pnAutoPause) {
		super(simulation);
		if (pnAutoPause) {
			this.pressPause(); // TD+CM: autostart as paused
		}
	}
	
	@Deprecated
	public void addTabbSetup(Integer pnEntityType, ArrayList<JTabbedPane> poTabbedPanes)
	{
		moTabbedPaneConfig.put(pnEntityType, poTabbedPanes);
	}
	
	@Deprecated
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
	
	@Deprecated
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
