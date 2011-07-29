/**
 * CHANGELOG
 * 
 * 2011/06/15 TD - extracted clsEntitySelector into a distinct file.
 * 2011/06/15 TD - added javadoc comments. code sanitation.
 */
package sim;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.JFrame;
import config.clsProperties;

/**
 * Generates a GUI that allows to change the number of entities within one entity group. In a property configuraiton file, several different groups
 * of entities/agents can be defined. Each group has a type of entity, a type of decision unit (if applicable), and a type of positioning. 
 * For debugging/testing purposes, it proved to be advantageous to have a quick possibility to change the number of entities created for such a group. 
 * This class creates a GUI where the in the property file existing entity groups are added and the numbers can be changed by up and down buttons. 
 * 
 * @author langr
 * 18.09.2009, 21:11:16
 * 
 */
public class clsBWFastEntityAdapter extends JDialog {
	private static final long serialVersionUID = -8502114600901820076L;
	
	/**
	 * Displays and changes the provided properties. Important: the changes or temporarily only and do not affect the file. Thus, upon next 
	 * start of the simulation the original values are provided.  
	 * 
	 * @author langr
	 * 15.06.2011, 16:35:00
	 *
	 * @param poParent JFrame of the calling GUI element
	 * @param poTitle Title for the window
	 * @param poProp Reference to the instance of the read property file that will be adapted.
	 */
	public clsBWFastEntityAdapter(JFrame poParent, String poTitle, clsProperties poProp) {
	    super(poParent, poTitle, true);
	    
	    if (poParent != null) {
	      Dimension parentSize = poParent.getSize(); 
	      Point p = poParent.getLocation(); 
	      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }

	    setSize(500, 600);
		setLayout(new GridLayout(0,1));
		int numGroups = poProp.getPropertyInt("entitygroups.numentitygroups");
		for (int i=0;i<numGroups;i++) {
			String oCountPath = "entitygroups."+i+".numentities";
			clsEntitySelector oEntitySelector = new clsEntitySelector(poProp.getPropertyInt(oCountPath), poProp.getProperty("entitygroups."+i+".groupentitytype"), oCountPath, poProp );
			oEntitySelector.setSize(400, oEntitySelector.getSize().height);
			add( oEntitySelector);
		}
		
	    pack();
	    
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	    setVisible(true); 
	}

}
