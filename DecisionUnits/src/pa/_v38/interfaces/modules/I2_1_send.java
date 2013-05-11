/**
 * I1_9_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:32:33
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eFastMessengerSources;




/**
 * Neurosymbols representing libido an the signals from the erogenous zones are transmitted from F40 to F64.
 * 
 * @author deutsch
 * 03.03.2011, 15:32:33
 * 
 */
public interface I2_1_send  {
	public void send_I2_1(Double poLibidoSymbol, HashMap<eFastMessengerSources, Double> poData);
}
