/**
 * clsE02InspectorOutput.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:47:14
 */
package inspectors.mind.pa._v30;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTree;

import pa.modules._v30.E02_NeurosymbolizationOfNeeds;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:47:14
 * 
 */
public class clsE02InspectorOutput extends Inspector {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	//private E02_NeurosymbolizationOfNeeds moE02; //never used!
	JTree moModuleTree;
	
    public clsE02InspectorOutput(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            E02_NeurosymbolizationOfNeeds poNeuroNeeds)
    {
		moOriginalInspector = originalInspector;
		//moE02= poNeuroNeeds; //never used!
		
		Box oBox1 = new Box(BoxLayout.PAGE_AXIS);
		JLabel myLable = new JLabel("E02Output");
		oBox1.add(myLable);
        setLayout(new BorderLayout());
        add(oBox1, BorderLayout.CENTER);
    }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:47:27
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO (langr) - Auto-generated method stub
		
	}

}
