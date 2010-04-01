/**
 * clsInspectorActionCommands.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 */
package inspectors;

import java.awt.BorderLayout;
import java.util.ArrayList;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;
import decisionunit.clsBaseDecisionUnit;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 * 
 */
public class clsInspectorActionCommands  extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 06.08.2009, 08:21:29
	 */
	private static final long serialVersionUID = 7969271764842942368L;

	class Entry {
		public int mnStartTime;
		public int mnEndTime;
		public String moActionCommand;
		
		public Entry(String poActionCommand) {
			mnStartTime = mnTimeCounter;
			mnEndTime = -1;
			moActionCommand = poActionCommand;
		}
		
		@Override
		public String toString() {
			String oResult = "";
			
			if (mnEndTime > 0) {
				oResult = mnStartTime+" - "+mnEndTime+": "+moActionCommand;
			} else {
				oResult = mnStartTime+" - __: "+moActionCommand;
			}
			
			return oResult;
		}
	}
	
    private ArrayList<Entry> moActionCommandHistory;
    private int mnMaxHistoryLength = 20;
    protected int mnTimeCounter;

	
	public Inspector moOriginalInspector;
	private clsBaseDecisionUnit moDU;
//	private Controller moConsole; // TD - warning free
//	private JLabel moCaption; // TD - warning free
	
	HTMLBrowser moHTMLPane;
	
	private String getActionCommands() {
		String oResult = "";
		
		String currentAction = moDU.getActionProcessorToHTML();
		
		if (moActionCommandHistory.size() > 0) {
			Entry oLatestEntry = moActionCommandHistory.get(moActionCommandHistory.size()-1);
			if (oLatestEntry != null) {
				if (!oLatestEntry.moActionCommand.equals(currentAction)) {
					oLatestEntry.mnEndTime = mnTimeCounter-1;
					Entry oNewEntry = new Entry(currentAction);
					moActionCommandHistory.add(oNewEntry);
				}
			}
			
			while (moActionCommandHistory.size() > mnMaxHistoryLength) {
				moActionCommandHistory.remove(0);
			}
		} else {
			Entry oNewEntry = new Entry(currentAction);
			moActionCommandHistory.add(oNewEntry);
		}
		
		oResult = "<ul>";
		for (Entry oEntry:moActionCommandHistory) {
			oResult += "<li>"+oEntry+"</li>";
		}
		oResult += "</ul>";
		
		return oResult;
	}
	
	public clsInspectorActionCommands(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBaseDecisionUnit poDU)
	{
		moOriginalInspector = originalInspector;
		moDU= poDU;
		//final SimState state=guiState.state;
//		moConsole=guiState.controller; // TD - warning free
		
//		moCaption = new JLabel("Layers of Brooks Subsumption Architecture"); // TD - warning free
        // creating the checkbox to sitch on/off the AI intelligence-levels.
		
		moActionCommandHistory = new ArrayList<Entry>();
		
        String contentData = "<html><head></head><body>";
        contentData+="<h1>Action Commands History (last "+mnMaxHistoryLength+" entries)</h1>";
        contentData+=getActionCommands();
        contentData+="</body></html>";
        
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(contentData);
		add(moHTMLPane, BorderLayout.CENTER);
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 03.08.2009, 13:35:21
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		mnTimeCounter++;
		
        String contentData = "<html><head><tr.font face='Courier'></head><body>";
        contentData+="<h1>Action Commands History (last "+mnMaxHistoryLength+" entries)</h1>";
        contentData+=getActionCommands();
        contentData+="</body></html>";
        moHTMLPane.setText(contentData);
	}

}
