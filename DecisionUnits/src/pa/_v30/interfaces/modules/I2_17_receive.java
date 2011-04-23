/**
 * I2_17_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;

import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 * 
 */
public interface I2_17_receive {
	public void receive_I2_17(ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates);
}
