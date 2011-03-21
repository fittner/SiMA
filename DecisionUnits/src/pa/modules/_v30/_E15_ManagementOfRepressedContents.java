/**
 * E15_ManagementOfRepressedContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.List;

import config.clsBWProperties;
import pa.interfaces.receive._v30.I2_5_receive;
import pa.interfaces.receive._v30.I2_6_receive;
import pa.interfaces.receive._v30.I4_1_receive;
import pa.interfaces.receive._v30.I4_2_receive;
import pa.interfaces.receive._v30.I4_3_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.modules._v19.clsModuleContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 * 
 */
public class _E15_ManagementOfRepressedContents extends clsModuleContainer implements I2_5_receive, I4_1_receive, I4_2_receive, I2_6_receive, I4_3_receive {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 19:29:44
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 * @param poInterfaceHandler
	 * @param poMemory
	 * @param poKnowledgeBaseHandler
	 */
	public _E15_ManagementOfRepressedContents(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer,
			pa._v19.clsInterfaceHandler poInterfaceHandler, clsMemory poMemory,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory,
				poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);
	}


	public static final String P_S_1 = "S_1";
	public static final String P_S_2 = "S_2";
	
	public E35_EmersionOfRepressedContent moS_ManagementOfRepressedContents_1;
	public E36_RepressionHandler moS_ManagementOfRepressedContents_2;

	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll(E35_EmersionOfRepressedContent.getDefaultProperties(pre+P_S_1));
		oProp.putAll(E36_RepressionHandler.getDefaultProperties(pre+P_S_2));
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
//		moS_ManagementOfRepressedContents_1 = new E35_EmersionOfRepressedContent(pre+P_S_1, poProp, this, moInterfaceHandler);
//		moS_ManagementOfRepressedContents_2 = new E36_RepressionHandler(pre+P_S_2, poProp, this, moInterfaceHandler);
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
//		moS_ManagementOfRepressedContents_1.receive_I2_5(poEnvironmentalTP);
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
//	@Override
//	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
//		((I4_3_receive)moEnclosingContainer).receive_I4_3(poPIs);
//	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 19:29:20
	 * 
	 * @see pa.interfaces.receive._v30.I4_3_receive#receive_I4_3(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void receive_I4_3(ArrayList<clsPrimaryDataStructureContainer> poPIs) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
