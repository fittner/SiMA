/**
 * I5_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:07
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsAffectTension;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:07
 * 
 */
public interface I5_1_receive extends I_BaseInterface {
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList_old,
			  ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList);
}
