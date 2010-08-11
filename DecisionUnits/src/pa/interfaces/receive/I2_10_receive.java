/**
 * I2_10.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 * 
 */
public interface I2_10_receive extends I_BaseInterface {
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception_old,
			   ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception);
}
