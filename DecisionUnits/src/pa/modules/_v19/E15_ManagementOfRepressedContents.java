/**
 * E15_ManagementOfRepressedContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.List;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I2_5_receive;
import pa.interfaces.receive._v19.I2_6_receive;
import pa.interfaces.receive._v19.I4_1_receive;
import pa.interfaces.receive._v19.I4_2_receive;
import pa.interfaces.receive._v19.I4_3_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 * 
 */
public class E15_ManagementOfRepressedContents extends clsModuleContainer implements I2_5_receive, I4_1_receive, I4_2_receive, I2_6_receive, I4_3_receive {

	public static final String P_S_1 = "S_1";
	public static final String P_S_2 = "S_2";
	
	public S_ManagementOfRepressedContents_1 moS_ManagementOfRepressedContents_1;
	public S_ManagementOfRepressedContents_2 moS_ManagementOfRepressedContents_2;
	
	/**
	 * 
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
			clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
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
	
		moS_ManagementOfRepressedContents_1 = new S_ManagementOfRepressedContents_1(pre+P_S_1, poProp, this, moInterfaceHandler);
		moS_ManagementOfRepressedContents_2 = new S_ManagementOfRepressedContents_2(pre+P_S_2, poProp, this, moInterfaceHandler);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
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
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
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
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
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
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		((I2_6_receive)moEnclosingContainer).receive_I2_6(poPerceptPlusRepressed);		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
		((I4_3_receive)moEnclosingContainer).receive_I4_3(poPIs);
	}

}
