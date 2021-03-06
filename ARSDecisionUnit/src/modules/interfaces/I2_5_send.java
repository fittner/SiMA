/**
 * I8_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:53:28
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * Connects the last psychic module in the chain F30 to the neurodesymbolization of the actions F31.	
 * 
 * @author deutsch
 * 18.05.2010, 14:53:28
 * 
 */
public interface I2_5_send {
	public void send_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands, clsWordPresentationMesh moWordingToContext);
}
