/**
 * @author deutsch
 * 12.05.2009, 17:11:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.internalSystems.clsFlesh;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 17:11:53
 * 
 */
public class clsMeatBody extends clsBaseBody {
	private clsFlesh moFlesh;
	private float mrRegrowRate;

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 12.05.2009, 17:11:58
	 *
	 * @param poEntity
	 * @param poConfig
	 */
	public clsMeatBody(clsEntity poEntity, clsConfigMap poConfig) {
		super(poEntity, poConfig);
	
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 17:11:53
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub

	}

}
