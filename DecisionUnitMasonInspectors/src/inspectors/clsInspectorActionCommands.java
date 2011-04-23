/**
 * clsInspectorActionCommands.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author deutsch
 * 05.08.2009, 13:22:55
 */
package inspectors;

import inspectors.mind.pa._v30.autocreated.TextOutputPanel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import sim.portrayal.Inspector;
import statictools.clsSimState;
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
    private long mnMaxHistoryLength = 200;
    protected long mnTimeCounter;
	private clsBaseDecisionUnit moDU;
	TextOutputPanel moTextPane;
	public static final String newline = System.getProperty("line.separator");
	
	class Entry {
		public long mnStartTime;
		public long mnEndTime;
		public String moActionCommand;
		
		public Entry(String poActionCommand) {
			mnStartTime = clsSimState.getSteps();
			mnEndTime = -1;
			moActionCommand = poActionCommand;


		}
		
		@Override
		public String toString() {
			String oResult = "";
			String oDisplayName = moActionCommand;
			
			if (oDisplayName.equals("")) {
				oDisplayName = "<i>n/a</i>";
			}
			
			if (mnEndTime > 0) {
				oResult = "["+mnStartTime+" - "+mnEndTime+"]: "+oDisplayName;
			} else {
				oResult = "["+mnStartTime+" - __]: "+oDisplayName;
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
	
		for (Entry oEntry:moActionCommandHistory) {
			oResult += oEntry+newline;
		}
		
		return oResult;
	}
	
	private String getContent() {
        String contentData = "";
        contentData+="** Action Commands History (last "+mnMaxHistoryLength+" entries) **"+newline+newline;
        contentData+=getActionCommands();
        
        return contentData;
	}
	
	public clsInspectorActionCommands(clsBaseDecisionUnit poDU) {
		moDU= poDU;
		moActionCommandHistory = new ArrayList<Entry>();
       
        setLayout(new BorderLayout());
    	moTextPane = new TextOutputPanel( getContent() );
		add(moTextPane, BorderLayout.CENTER);
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
		mnTimeCounter = clsSimState.getSteps();
        moTextPane.setText(  getContent() );
	}
}
