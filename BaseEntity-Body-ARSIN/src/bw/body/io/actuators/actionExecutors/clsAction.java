/**
 * CHANGELOG
 *
 * Apr 30, 2013 herret - File created
 *
 */
package bw.body.io.actuators.actionExecutors;

import bw.entities.base.clsEntity;
/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Apr 30, 2013, 9:22:05 AM
 * 
 */
public class clsAction{

	
	private int mnLiveTime;
	private String moActionName;
	private clsEntity moCorrespondingEntity;
	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since Apr 30, 2013 9:22:09 AM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsAction(int pnLiveTime) {
		mnLiveTime = pnLiveTime;
		moCorrespondingEntity=null;		
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


	public String getActionName(){
		return moActionName;
	}
	public void setActionName(String poName){
		moActionName = poName;
	}


}
