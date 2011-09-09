/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.tools.clsPair;



/**
 * Read access to blocked content (from F35).
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D2_2_receive {
	public clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> receive_D2_2();
}
