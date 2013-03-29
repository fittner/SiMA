/**
 * CHANGELOG
 *
 * 29.03.2013 Jordakieva - File created
 *
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;

import panels.TextOutputPanel;
import bw.entities.tools.clsInventory;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 29.03.2013, 16:06:02
 * 
 */
public class clsInspectorInventory extends Inspector {
	
	//private static final TextOutputPanel TextOutputPanel () = null;
	private clsInventory moInventory = null;
	private TextOutputPanel moText;
	
	public clsInspectorInventory (clsInventory poEntity)	{
		moInventory = poEntity;
		moText = new TextOutputPanel ();
		setLayout (new BorderLayout());
		
		try {
			add (moText, BorderLayout.CENTER);
		} catch (Exception e) { 
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 29.03.2013 16:06:02
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO (Jordakieva) - Auto-generated method stub
				
		try {
			if (isVisible()) {
				moText.setText(moInventory.toText());
			}
		} catch (Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}

	}

}
