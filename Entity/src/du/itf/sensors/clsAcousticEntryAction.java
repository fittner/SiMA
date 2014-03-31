/**
 * CHANGELOG
 *
 * 19.11.2013 herret - File created
 *
 */
package du.itf.sensors;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 19.11.2013, 13:34:42
 * 
 */
public class clsAcousticEntryAction {
	protected String moName;
	protected clsAcousticEntry moObject;
	
	
	/**
	 * @since 19.11.2013 13:36:08
	 * 
	 * @return the moName
	 */
	public String getActionName() {
		return moName;
	}
	/**
	 * @since 19.11.2013 13:36:08
	 * 
	 * @param moName the moName to set
	 */
	public void setActionName(String moName) {
		this.moName = moName;
	}
	/**
	 * @since 19.11.2013 13:36:08
	 * 
	 * @return the moObject
	 */
	public clsAcousticEntry getObjectAcousticEntry() {
		return moObject;
	}
	/**
	 * @since 19.11.2013 13:36:08
	 * 
	 * @param moObject the moObject to set
	 */
	public void setObject(clsAcousticEntry moObject) {
		this.moObject = moObject;
	}
	
	@Override
	public String toString(){
		String oRetVal="";
		oRetVal += moName;
		if(moObject != null){
			oRetVal +=" (" + moObject.mnEntityType.toString() +")";
		}
		return oRetVal;
	}

	

}
