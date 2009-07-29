/**
 * @author zeilinger
 * 22.07.2009, 15:43:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import bw.entities.clsEntity;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.07.2009, 15:43:50
 * 
 */
public class clsInspectorSensor extends Inspector {

	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.07.2009, 15:44:47
	 */
	private static final long serialVersionUID = 1L;
	
	clsEntity moHostEntity; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.07.2009, 16:47:33
	 *
	 * @param originalInspector
	 * @param wrapper
	 * @param guiState
	 * @param poEntity
	 */
	public clsInspectorSensor(Inspector originalInspector,
							  LocationWrapper wrapper, 
							  GUIState guiState, 
					          clsEntity poEntity) {
     		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 17:18:53
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO Auto-generated method stub
		
	}
}
