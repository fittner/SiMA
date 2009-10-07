/**
 * E15_ManagementOfRepressedContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsThingPresentationMesh;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.memory.clsMemory;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 * 
 */
public class E15_ManagementOfRepressedContents extends clsModuleContainer implements I2_5, I4_1, I4_2, I2_6, I4_3 {

	public static final String P_E15_1 = "E15_1";
	public static final String P_E15_2 = "E15_2";
	
	public E15_1_ManagementOfRepressedContents moE15_1_ManagementOfRepressedContents;
	public E15_2_ManagementOfRepressedContents moE15_2_ManagementOfRepressedContents;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 07.10.2009, 11:28:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 * @param poMemory
	 */
	public E15_ManagementOfRepressedContents(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer,
			clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}

	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll(E15_1_ManagementOfRepressedContents.getDefaultProperties(pre+P_E15_1));
		oProp.putAll(E15_2_ManagementOfRepressedContents.getDefaultProperties(pre+P_E15_2));
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE15_1_ManagementOfRepressedContents = new E15_1_ManagementOfRepressedContents(pre+P_E15_1, poProp, this);
		moE15_2_ManagementOfRepressedContents = new E15_2_ManagementOfRepressedContents(pre+P_E15_2, poProp, this);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsThingPresentationMesh> poEnvironmentalTP) {
		moE15_1_ManagementOfRepressedContents.receive_I2_5(poEnvironmentalTP);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(int pnData) {
		moE15_2_ManagementOfRepressedContents.receive_I4_1(pnData);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(int pnData) {
		moE15_2_ManagementOfRepressedContents.receive_I4_2(pnData);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		((I2_6)moEnclosingContainer).receive_I2_6(pnData);		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(int pnData) {
		((I4_3)moEnclosingContainer).receive_I4_3(pnData);
	}

}
