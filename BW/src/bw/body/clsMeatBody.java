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
import bw.utils.enums.eNutritions;

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
	private float mrMaxWeight;

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
	
		moFlesh = new clsFlesh((clsConfigMap) moConfig.get(eConfigEntries.INTSYS_FLESH));
	}

	private void applyConfig() {
		
		clsConfigMap oFleshConfig = (clsConfigMap) moConfig.get(eConfigEntries.INTSYS_FLESH);
		mrRegrowRate = ((clsConfigFloat)oFleshConfig.get(eConfigEntries.INCREASERATE)).get();
		mrMaxWeight  = ((clsConfigFloat)oFleshConfig.get(eConfigEntries.MAXCONTENT)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		clsConfigMap oExt = new clsConfigMap();
		oExt.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(false));	
		oDefault.add(eConfigEntries.EXTERNAL_IO, oExt);
		oDefault.add(eConfigEntries.INTERNAL_IO, oExt);


		clsConfigMap oFlesh = new clsConfigMap();		
		
		clsConfigMap oNutritions = new clsConfigMap();
		
		oNutritions.add(eConfigEntries.FAT, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.PROTEIN, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.VITAMIN, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.CARBOHYDRATE, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.WATER, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.MINERAL, new clsConfigFloat(1.0f));
		oNutritions.add(eConfigEntries.TRACEELEMENT, new clsConfigFloat(1.0f));
		
		oFlesh.add(eConfigEntries.NUTRITIONS, oNutritions);
		oFlesh.add(eConfigEntries.CONTENT, new clsConfigFloat(10.0f));
		oFlesh.add(eConfigEntries.MAXCONTENT, new clsConfigFloat(20.0f));
		oFlesh.add(eConfigEntries.INCREASERATE, new clsConfigFloat(0.01f));
		
		oDefault.add(eConfigEntries.INTSYS_FLESH, oFlesh);
		
		return oDefault;
	}	
		
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 17:11:53
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	//@Override
	public void stepUpdateInternalState() {
		if (moFlesh.getAmount() < mrMaxWeight) {
			moFlesh.grow(mrRegrowRate);
		}
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
