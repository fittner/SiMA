/**
 * clsInspectorActionCommands.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 */
package inspectors;

import java.awt.BorderLayout;
import java.util.ArrayList;
import sim.portrayal.Inspector;
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
	private static final long serialVersionUID = 7969271764842942368L;
    private ArrayList<Entry> moActionCommandHistory;
    private int mnMaxHistoryLength = 20;
    protected int mnTimeCounter;
	private clsBaseDecisionUnit moDU;
	HTMLBrowser moHTMLPane;
	
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
	
	private String getContent() {
        String contentData = "<html><head></head><body>";
        contentData+="<h1>Action Commands History (last "+mnMaxHistoryLength+" entries)</h1>";
        contentData+=getActionCommands();
        contentData+="</body></html>";
        
        return contentData;
	}
	
	public clsInspectorActionCommands(clsBaseDecisionUnit poDU) {
		moDU= poDU;
		moActionCommandHistory = new ArrayList<Entry>();
       
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser( getContent() );
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
        moHTMLPane.setText(  getContent() );
	}
}
