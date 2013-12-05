/**
 * I1_9_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:31:45
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eFastMessengerSources;




/**
 * Neurosymbols representing libido an the signals from the erogenous zones are transmitted from F40 to F64.
 * 
 * @author deutsch
 * 03.03.2011, 15:31:45
 * 
 */
public interface I2_1_receive  {
	public void receive_I2_1(Double poLibidoSymbol ,HashMap<eFastMessengerSources, Double> poData);
}