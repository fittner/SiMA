/**
 * I8_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:28
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * Connects the last psychic module in the chain F30 to the neurodesymbolization of the actions F31.	
 * 
 * @author deutsch
 * 11.08.2009, 14:53:28
 * 
 */
public interface I2_5_receive {
	public void receive_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands, clsWordPresentationMesh moWordingToContext2);
}
