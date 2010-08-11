/**
 * I1_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:02:10
 */
package pa.interfaces.receive;

import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:02:10
 * 
 */
public interface I1_5_receive extends I_BaseInterface {
	public void receive_I1_5(List<clsPrimaryInformation> poData_old,
			  List<clsPrimaryDataStructureContainer> poData);
}
