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
import bw.utils.container.clsConfigBoolean;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eConfigEntries;

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
		super(poEntity, getFinalConfig(poConfig));
		applyConfig();
	
		moFlesh = new clsFlesh((clsConfigMap) moConfig.get(eBodyParts.INTSYS_FLESH));
	}

	private void applyConfig() {
		mrRegrowRate = ((clsConfigFloat)moConfig.get(eConfigEntries.INCREASERATE)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.INCREASERATE, new clsConfigFloat(0.01f));
		
		clsConfigMap oExt = new clsConfigMap();
		oExt.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(false));	
		oDefault.add(eBodyParts.EXTERNAL_IO, oExt);

		return oDefault;
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
		moFlesh.grow(mrRegrowRate);

	}

	/**
	 * @author deutsch
	 * 12.05.2009, 17:53:30
	 * 
	 * @return the moFlesh
	 */
	public clsFlesh getFlesh() {
		return moFlesh;
	}

}
