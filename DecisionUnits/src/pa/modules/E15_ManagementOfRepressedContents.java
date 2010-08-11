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
import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I2_5_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I4_3_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
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
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP_old, ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moS_ManagementOfRepressedContents_1.receive_I2_5(poEnvironmentalTP_old, poEnvironmentalTP);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs_old, List<clsThingPresentation> poTPs_old, List<clsAffectTension> poAffects_old,
			  List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		moS_ManagementOfRepressedContents_2.receive_I4_1(poPIs_old, poTPs_old, poAffects_old, poPIs, poTPs, poAffects);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:29:51
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs_old, ArrayList<clsThingPresentation> poTPs_old, ArrayList<clsAffectTension> poAffects_old,
			  ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		moS_ManagementOfRepressedContents_2.receive_I4_2(poPIs_old, poTPs_old, poAffects_old, poPIs, poTPs, poAffects);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_old,
			  				ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poPerceptPlusRepressed) {
		((I2_6_receive)moEnclosingContainer).receive_I2_6(poPerceptPlusRepressed_old, poPerceptPlusRepressed);		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:32:12
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs_old, List<clsPrimaryDataStructureContainer> poPIs) {
		((I4_3_receive)moEnclosingContainer).receive_I4_3(poPIs_old, poPIs);
	}

}
