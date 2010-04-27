/**
 * E15_ManagementOfRepressedContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import config.clsBWProperties;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.memory.clsMemory;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 * 
 */
public class E15_ManagementOfRepressedContents extends clsModuleContainer implements I2_5, I4_1, I4_2, I2_6, I4_3 {

	public static final String P_S_1 = "S_1";
	public static final String P_S_2 = "S_2";
	
	public S_ManagementOfRepressedContents_1 moS_ManagementOfRepressedContents_1;
	public S_ManagementOfRepressedContents_2 moS_ManagementOfRepressedContents_2;
	
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

		oProp.putAll(S_ManagementOfRepressedContents_1.getDefaultProperties(pre+P_S_1));
		oProp.putAll(S_ManagementOfRepressedContents_2.getDefaultProperties(pre+P_S_2));
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moS_ManagementOfRepressedContents_1 = new S_ManagementOfRepressedContents_1(pre+P_S_1, poProp, this);
		moS_ManagementOfRepressedContents_2 = new S_ManagementOfRepressedContents_2(pre+P_S_2, poProp, this);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		moS_ManagementOfRepressedContents_1.receive_I2_5(poEnvironmentalTP);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffectTension> poAffects) {
		moS_ManagementOfRepressedContents_2.receive_I4_1(poPIs, poTPs, poAffects);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs, ArrayList<clsThingPresentation> poTPs, ArrayList<clsAffectTension> poAffects) {
		moS_ManagementOfRepressedContents_2.receive_I4_2(poPIs, poTPs, poAffects);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		((I2_6)moEnclosingContainer).receive_I2_6(poPerceptPlusRepressed);		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		((I4_3)moEnclosingContainer).receive_I4_3(poPIs);
	}

}
