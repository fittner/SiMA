/**
 * CHANGELOG
 *
 * Apr 30, 2013 herret - File created
 *
 */
package bw.body.io.actuators.actionExecutors;

import du.enums.eEntityType;
import bfg.utils.enums.ePercievedActionType;
import bw.entities.clsEntity;
/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Apr 30, 2013, 9:22:05 AM
 * 
 */
public class clsAction{

	
	private int mnLiveTime;
	private ePercievedActionType moActionType;
	private clsEntity moCorrespondingEntity;
	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since Apr 30, 2013 9:22:09 AM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsAction(int pnLiveTime, ePercievedActionType poActionType) {

		mnLiveTime = pnLiveTime;
		moCorrespondingEntity=null;
		moActionType = poActionType;
		
	}
	
	public boolean step(){
		mnLiveTime--;
		if(mnLiveTime<=0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void attachEntity(clsEntity poEntity){
		moCorrespondingEntity=poEntity;
	}
	
	public clsEntity getCorrespondingEntity(){
		return moCorrespondingEntity;
	}
	public eEntityType getCorrespondingEntityType(){
		if(moCorrespondingEntity!=null){
			return moCorrespondingEntity.getEntityType();
		}
		return null;
	}

	public ePercievedActionType getActionType(){
		return moActionType;
	}


}
