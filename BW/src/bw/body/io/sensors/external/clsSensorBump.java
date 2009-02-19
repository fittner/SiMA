/**
 * @author zia
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.clsEntity;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class clsSensorBump extends clsSensorExt{

	private clsEntity moEntity;
	
	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorBump(clsEntity poEntity) {
		super();
		setEntity(poEntity);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
	private void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.external.clsSensorExt#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
}
