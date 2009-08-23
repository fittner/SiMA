/**
 * clsPAInspectorTopDown.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 03:13:49
 */
package inspectors.mind.pa;


import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import pa.modules.C00_PsychicApparatus;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import statictools.clsGetARSPath;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 03:13:49
 * 
 */
public class clsPAInspectorTopDown extends Inspector {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private C00_PsychicApparatus moPsychicApparatus;
	private JScrollPane moScrollPane;

    public clsPAInspectorTopDown(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            C00_PsychicApparatus poPsychicApparatus)
    {
		moOriginalInspector = originalInspector;
		moPsychicApparatus = poPsychicApparatus;

		String oStr = clsGetARSPath.getArsPath() + "/DecisionUnits/src/resources/images/top_down.gif";
		Icon image = new ImageIcon( oStr );
		JLabel label = new JLabel( image );

		// Create a tabbed pane
		moScrollPane = new JScrollPane();
		moScrollPane.getViewport().add( label );

		add(moScrollPane, BorderLayout.WEST);
    }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {

	}

}